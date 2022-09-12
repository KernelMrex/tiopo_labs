from typing import Optional

from type.args import *


def parse_exec_args(args: list) -> Optional[ExecArgs]:
    if len(args) != 3:
        return None
    return ExecArgs(configPath=args[1], execPath=args[2])