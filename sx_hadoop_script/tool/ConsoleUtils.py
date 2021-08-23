from tool import LinuxShell
from tool import LocalLinuxCommand
from utils import Constant
def cmd(cmd):
    if Constant.SHELL_FLAG:
        cmdShell = LinuxShell.SSHCommand();
        cmdShell.cmd("su hadoop")
        cmdShell.cmd("cd /home/app/test/sx_hadoop_script/")
        cmdShell.cmd(cmd)
        cmdShell.close()
    else:
        LocalLinuxCommand.actionLocalCmd(cmd)
