from dataclasses import dataclass


@dataclass
class FailedTest:
    testName: str
    errorMsg: str
    returnCode: int


@dataclass
class PassedTest:
    testName: str
