# import libs
import sys
import string
#import hashlib
"""
# -----------------------------------------------------------------------------
# this is the hive entry point:
# parameters are accepted as a tab delimited stream
# the stream must be read and broken
# variables will be assigned to the 'parameters' passed
# as this is payment profile history,
#    most variables should be cast to maps / dict
# PARAMETERS EXPEXTED:
#   1) current_statement_month          as string           to  int
#   2) first_statement_month            as string           to  int
#   3) account_transaction_profiles     as formatted string to  dict of int:string
#   4) account_months_in_areers         as formatted string to  dict of int:int
#
# -----------------------------------------------------------------------------
# 201510:9 ,201511:9 ,201512:9 ,201601:9 ,201602:9 ,201603:9 ,201604:9
# ,201605:9 ,201606:9 ,201607:9 ,201608:9 ,201609:9 ,201610:9
"""
def parse_string_to_dict(input_string, value_format):
    lst_input = input_string.split(",")
    dict_input_data = {}
    for i in lst_input:
        key_value = i.split(":")
        m = int(key_value[0])
        if value_format == 4:
            dict_input_data[m] = long(key_value[1])
        elif value_format == 3:
            dict_input_data[m] = int(key_value[1])
        else:
            dict_input_data[m] = key_value[1].strip()
    return dict_input_data

# -----------------------------------------------------------------------------
# this function builds an index of the trans profile year months in DEC order
# the current statement month  will be the start of the array and the
# first statement month will be calculated in the hive query
# we will use this index to align payment profile data over a period of time
# -----------------------------------------------------------------------------
def build_history_date_index(i_fst_stm_mnth, i_curr_stm_mnth):
    i_fst_stm = i_fst_stm_mnth
    i_cur_stm = i_curr_stm_mnth
    lst_curr_stm = list(divmod(i_cur_stm, 100))
    lst_fst_stm = list(divmod(i_fst_stm, 100))

    if ((lst_curr_stm[0]*12)+lst_curr_stm[1]) - ((lst_fst_stm[0]*12)+lst_fst_stm[1]) < 24 :
        i_fst_stm = ((lst_curr_stm[0]-2)*100) + lst_curr_stm[1]
    lst_date_index = [i_cur_stm]
    while i_cur_stm > i_fst_stm:
        if lst_curr_stm[1] == 1:
            lst_curr_stm[1] = 12
            lst_curr_stm[0] = lst_curr_stm[0] - 1
        else:
            lst_curr_stm[1] = lst_curr_stm[1] - 1
        i_cur_stm = lst_curr_stm[0] * 100 + lst_curr_stm[1]
        lst_date_index.append(i_cur_stm)
    return lst_date_index


# -----------------------------------------------------------------------------
# this function is used to sort the Payment Profile maps recieved
# the order will be dec from the current statement month calculated in
# build_history_date_index
# -----------------------------------------------------------------------------
def build_pp_history(lst_date_index, dict_payment_profile):
    dx = lst_date_index
    items_in_list = len(dx)
    if items_in_list < 24:
        items_in_list = 24
    else:
        items_in_list = len(dx)
    lst_orderd_pp_history = ['='] * items_in_list
    x = 0
    for i in dx:
        if i in dict_payment_profile:
            lst_orderd_pp_history[x] = dict_payment_profile[i]
            x = x + 1
        else:
            x = x + 1
    return lst_orderd_pp_history

# -----------------------------------------------------------------------------
# this function is to take the PP trans profile history strings and
# apply amnesty display rules to them
# as we are building history for the ENTIRE history of an account
# we will have to apply manesty to pre- april 2014 data
# we will also have to apply NCAA to post april 2014 data
# the rules are:
#   1) remove all adverse codes where status date < 2014-04-01
#       and replace with months in areers
#   post 2014-04-01
#   1) remove all adverse status codes that occur AFTER a paidup status
#       by replacing the adverse code with months in areers
#   2) treat a paid up adverse code (X) as a paid up code (P)
#       also convert the code (X) to (P)
#   3) handle a partial paidup code (U) as a months in areers
# -----------------------------------------------------------------------------
def apply_amnesty(lst_date_index,lst_orderd_pp_hist, lst_ordered_pp_mia):
    lst_adverse_codes = ['W', 'I', 'J', 'L', 'AA', 'AC', 'U']
    lst_paid_up_codes = ['X', 'U', 'C', 'P', 'T', 'V']
    i_num_occ = len(lst_date_index)
    lst_trans_profile_amnesty = list(lst_orderd_pp_hist)
    #we need to work backwards throug the list- so we reverse all of them
    lst_trans_profile_amnesty.reverse()
    lst_orderd_pp_hist.reverse()
    lst_ordered_pp_mia.reverse()
    lst_date_index.reverse()
    # set the amnesty date
    i_amnesty_date = 201404
    # get index of most recent paidup status
    i_first_paid_up_occurance = -1
    i_cnt = 0
    for transp_cde in lst_orderd_pp_hist:
        for paid_cde in lst_paid_up_codes:
            if transp_cde == paid_cde:
                i_first_paid_up_occurance = i_cnt
                break
            elif lst_date_index[i_cnt] == i_amnesty_date:
                i_first_paid_up_occurance = i_cnt
                break
            else:
                continue
        if i_first_paid_up_occurance != -1: break
        i_cnt = i_cnt + 1
    for x in range(i_cnt, i_num_occ):
        for adv_cde in lst_adverse_codes:
            if lst_orderd_pp_hist[x] == adv_cde:
                lst_trans_profile_amnesty[x] = lst_ordered_pp_mia[x]
            elif lst_orderd_pp_hist[x] == 'X':
                lst_trans_profile_amnesty[x] = 'P'
    #now we need to reverse the strings again for presentation , yes I know it is lazy
    lst_trans_profile_amnesty.reverse()
    lst_orderd_pp_hist.reverse()
    lst_ordered_pp_mia.reverse()
    lst_date_index.reverse()
    return lst_trans_profile_amnesty

while True:
    hive_input = sys.stdin.readline()
    if not hive_input:
        break
    hive_input = string.strip(hive_input, "\n")
    isn, first_statement_month, current_statement_month, account_transaction_profiles, account_months_in_areers =hive_input.split('\t')
    i_current_statement_month = int(current_statement_month)
    i_first_statement_month = int(first_statement_month)
    dict_account_trans_profiles = parse_string_to_dict(account_transaction_profiles, 1)
    dict_account_months_in_areers = parse_string_to_dict(account_months_in_areers, 1)
    #build strings
    lst_history_date_index = build_history_date_index(i_first_statement_month, i_current_statement_month)
    lst_ordered_pp_history = build_pp_history(lst_history_date_index, dict_account_trans_profiles)
    lst_ordered_mia = build_pp_history(lst_history_date_index, dict_account_months_in_areers)
    lst_pp_history_amnesty = apply_amnesty(lst_history_date_index, lst_ordered_pp_history, lst_ordered_mia)
    # return values
    #print '\t'.join([str_history_date_index, str(lst_ordered_mia), str(lst_ordered_pp_history), str(lst_pp_history_amnesty)])
    str_history_date_index = '|'.join(str(e) for e in lst_history_date_index)
    str_pp_hist = '|'.join(str(e) for e in lst_ordered_pp_history)
    str_pp_amnesty = '|'.join(str(e) for e in lst_pp_history_amnesty)
    str_pp_mia = '|'.join(str(e) for e in lst_ordered_mia)
    #print '\t'.join([isn, '|'.join(str(e) for e in lst_history_date_index), '|'.join(str(e) for e in  lst_ordered_mia), '|'.join(str(e) for e in lst_ordered_pp_history), '|'.join(str(e) for e in lst_pp_history_amnesty)])
    print '\t'.join([isn, str_history_date_index, str_pp_mia,str_pp_hist, str_pp_amnesty])
