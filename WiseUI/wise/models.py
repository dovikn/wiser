from django.db import models

# Create your models here.
class triggers(models.Model):
	id = models.CharField(max_length = 30, primary_key=True)
	name = models.CharField(max_length = 50)
	type_id = models.IntegerField(default=1)
	is_enabled = models.IntegerField(default=1)