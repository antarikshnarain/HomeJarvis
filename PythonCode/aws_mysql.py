#!/usr/bin/python
# -*- coding: utf-8 -*-

import _mysql
import sys, string, os
from subprocess import call
import time

pinlst={}

for i in range(1000):
	try:
		print "Making SQL Connection"
		# website, username, password, dbname
		con = _mysql.connect('arduinoyuniot.cwlgwf0etvez.ap-southeast-1.rds.amazonaws.com', 'arduinoyunpro', 'arduinoyunpro', 'iot')
		print "Connection Success"
		# Query to database
		sqlstr="select * from deviceStatus"
		print  sqlstr
		con.query(sqlstr)
		result = con.use_result()
		data = result.fetch_row()
		while data!=():
			mydata=data[0]
			deviceId=int(mydata[0])
			status=int(mydata[1])
			time=str(mydata[2])
			pin=int(mydata[3])
			if not(pinlst.has_key(deviceId)) or pinlst[deviceId] != (pin,status):
				pinlst[deviceId] = (pin,status)
				print "Updating Pin:",pin," with status:",status
				# Update pin
				httpstr="http://localhost/arduino/digital/"+pin+"/"+status
				call(["curl",httpstr])
			data=result.fetch_row()
	except _mysql.Error, e:
		print "Error %d: %s" % (e.args[0], e.args[1])
		sys.exit(1)
	finally:
		if con:
			con.close()

