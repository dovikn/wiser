from django.shortcuts import render
from django.shortcuts import render_to_response
from wise.models import triggers

def home(request):
	triggs = triggers.objects.all()[:10]
	return render_to_response('index.html', {'triggs': triggs})