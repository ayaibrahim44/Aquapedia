import pandas as pd
import numpy as np
import matplotlib.pylab as plt
%matplotlib inline
from matplotlib.pylab import rcParams
import time 
import datetime
rcParams['figure.figsize'] = 15, 6

data = pd.read_csv('Downloads/Water_WHW.csv')
print(data)

data['unix_ts']= pd.to_datetime(data['unix_ts'],unit='s')
print(data)
#Get current year
i = datetime.datetime.now()
print ("Current year = %s" %i.year)

#print(data.head())
