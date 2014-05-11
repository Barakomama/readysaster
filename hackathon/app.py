# Created by Aia Sia	10 May 2014
# This is the server for a POST-CALAMITY DAMAGE ASSESSMENT mobile app that constructs reports validated by SMS messages.

from flask_peewee.admin import Admin, ModelAdmin, AdminPanel
from flask_peewee.db import Database
from flask_peewee.auth import Auth
from flask_peewee.rest import RestAPI, RestResource, UserAuthentication

from wtforms import TextField
from flask.ext.wtf import Form
from wtforms.validators import DataRequired
from flask.ext.wtf.file import FileField, FileRequired, FileAllowed

from peewee import *
from flask import *
import MySQLdb
import datetime
import json

import config
import requests
import threading

DATABASE 	= { 'name': 'readysaster', 'engine': 'peewee.MySQLDatabase', 'user': 'root', 'passwd': 'root', }
DEBUG 		= True
SECRET_KEY  = 'readysaster'
application = Flask(__name__)
application.config.from_object(__name__)

db 		= Database(application)
auth  	= Auth(application, db)
admin 	= Admin(application, auth)

numReports = 0

# Models ------------------------------------------------------------------------------------------

class FlashReport(db.Model):
	disaster_type	= CharField()
	lat				= FloatField()
	lng 			= FloatField()
	contact_number	= CharField(max_length=11)

	num_dead		= IntegerField()
	num_injured		= IntegerField()
	num_missing		= IntegerField()
	num_needfood	= IntegerField()
	num_needwater	= IntegerField()
	num_needshelter	= IntegerField()
	num_needsanit	= IntegerField()
	lifelinedamage	= CharField()

	created_at		= DateTimeField(default=datetime.datetime.now)
	# next_at			= DateTimeField(default=datetime.datetime.now+datetime.timedelta(hours=3))

class FlashReportAdmin(ModelAdmin):
	columns = ( 'disaster_type', 'datetime', 'latitude', 'longitude',
				'num_dead', 'num_injured', 'num_missing', 'num_needfood',
				'num_needwater', 'num_needshelter', 'num_needsanit')

# class InitialReport(db.Model):
#	fields for each sub-type

# class InterimReport(db.Model):
#	fields for each sub-type


# Rest API and other setup-------------------------------------------------------------------------

uauth = UserAuthentication(auth)
api = RestAPI(application, default_auth=uauth)

def siteSetup():
	auth.User.create_table(fail_silently=True)
	FlashReport.create_table(fail_silently=True)

	admin.register(FlashReport, FlashReportAdmin)
	auth.register_admin(admin)
	admin.setup()

	api.register(FlashReport)
	api.setup()

	try:
		myuser = auth.User(username='readysaster', email='readysaster@data.gov.ph', admin=True, active=True)
		myuser.set_password('readysaster')
		myuser.save()
	except Exception as e:
		pass

@application.route("/upload", methods=['GET', 'POST'])
def parseReports():
	if request.method == 'POST':
		print 'POSTed this: ' + str(request)

		raw_data = requests.raw_data
		map_url = 'http://v3.maptimize.com/api/maps/' + config.map_key + '/points'
		points  = []

		messages = raw_data.split('|')
		for m in messages:
			fields = raw_data.split(',')
			p = {}
			numReports += 1
			
			# p['id']		= numReports
			# p['lat']	= float(fields[x])				#  -90 < lat < 90
			# p['lng']	= float(fields[x])				# -180 < lng < 180
			# p['contact_number'] = fields[x]
			
			# p['disaster_type']	 = 
			# p['datetime'] 		 = 
			# p['num_dead']		 = 
			# p['num_injured']	 = 
			# p['num_missing']	 = 
			# p['num_needfood'] 	 =
			# p['num_needwater']   =
			# p['num_needshelter'] =
			# p['num_needsanit']	 =

			points.append(p)

		class PostToMaptimize(threading.Thread):
				points = []
				def __init__(self, points=[]):
					self.points = points
					threading.Thread.__init__(self)
				def run(self):
					for p in self.points:
					 	r_map = requests.post(map_url,
					 			auth=(config.map_auth_token, config.map_password),
					 			data=json.dumps(p))
					 	r_self = requests.post(url_for('/api/')
					 			auth=('readysaster', 'readysaster'),
					 			data=json.dumps(p))

		post = PostToMaptimize(points)
		post.start()

	return redirect(redirect_url())

@application.route("/")
def map():
	return render_template('map.html')

if __name__=="__main__":
	siteSetup()
	application.run(debug=True)