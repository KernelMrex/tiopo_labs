from dataclasses import dataclass


@dataclass
class ExecArgs:
    configPath: str
    execPath: str
