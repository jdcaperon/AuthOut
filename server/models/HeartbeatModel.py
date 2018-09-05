import time
import json

from cowpy import cow

class HeartbeatModel():

  time: int
  message: str

  def __init__(self):
    self.time = int(time.time())
    self.message = cow.Moose().milk("Server is up!")

  def __repr__(self):
    print(self.time)
    return json.dumps(self.__dict__)