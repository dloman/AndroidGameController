#!/usr/bin/python

from sys import argv
from socket import *

################################################################################
def SendBroadcast(ScreenId):
  print "Sending Broadcast Screen Id =", ScreenId
  broadcast = socket(AF_INET, SOCK_DGRAM)
  broadcast.bind(('', 0))
  broadcast.setsockopt(SOL_SOCKET, SO_BROADCAST, 1)
  broadcast.sendto(ScreenId, ('<broadcast>', 31337))

################################################################################
################################################################################
if __name__ == '__main__':
  if len(argv) != 2:
    print 'Usage: SendBroadcast ScreenId'
    exit()
  SendBroadcast(argv[1])

