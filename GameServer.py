#!/usr/bin/python
import sys
from SendBroadcast import SendBroadcast
from twisted.internet import reactor, protocol
from socket import *

################################################################################
################################################################################
class Echo(protocol.Protocol):
    """This is just about the simplest possible protocol"""

    def dataReceived(self, data):
      print 'data = ', data

################################################################################
################################################################################
if __name__ == '__main__':
  SendBroadcast('OneButton')
  factory = protocol.ServerFactory()
  factory.protocol = Echo
  reactor.listenTCP(42069, factory)
  reactor.run()
