from sketchfe.FeatureExtractor import *
from sketchfe import shapecreator
import sys

if len(sys.argv) == 2:
	loadedSketch = shapecreator.buildSketch('school',sys.argv[1].decode('string_escape'))
	fextractor = IDMFeatureExtractor()
	features = fextractor.extract(loadedSketch).tolist()
	print features
else:
	print "Usage: python %s <sketchinjsonformat>" % (sys.argv[0])
