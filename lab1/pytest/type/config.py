from dataclasses import dataclass
from typing import List, Optional


@dataclass
class FileStreamParams:
    path: str


@dataclass
class CaseParams:
    name: str
    description: str
    execArgs: List[str]
    input: Optional[FileStreamParams]
    output: Optional[FileStreamParams]
    expected: Optional[FileStreamParams]
    returnCode: int


@dataclass
class ConfigParams:
    caseParams: List[CaseParams]
