import re

from common.tool import LinuxShell, LocalLinuxCommand
from common.utils import Constant


class ConsoleUtil(object):
    def __init__(self):
        if Constant.SHELL_FLAG:
            self.cmdShell = LinuxShell.SSHCommand();
            self.cmdShell.cmd("su hadoop")
            self.cmdShell.cmd("cd %s" % Constant.SCRIPT_PATH)

    def cmd(self,cmd):
        if Constant.SHELL_FLAG:
            self.cmdShell.cmd(cmd)
        else:
            s1= re.sub(r'\\[\n|\r|\t]+', "", cmd)
            LocalLinuxCommand.actionLocalCmd(s1);

    def close(self):
        if Constant.SHELL_FLAG:
            self.cmdShell.close()