import pandas as pd
import matplotlib.pyplot as plt
plt.style.use('ggplot')

def getBestPerformingAgentOverTime():
	#print(df[df.columns[1:5]])
	print()
	x = df['Generation']
	for column in df.columns[1:5]:
		
		axes = plt.gca()
		axes.set_ylim([0,1])
		plt.subplot(221)
		plt.plot(x, df[column].values, label=column)
		plt.legend(loc='center left', bbox_to_anchor=(1, 0.5))


def getWorstPerformingAgentOverTime():
	#print(df[df.columns[5:9]])
	print()
	x = df['Generation']
	for column in df.columns[5:9]:

		axes = plt.gca()
		axes.set_ylim([0,1])
		plt.subplot(224)
		plt.plot(x, df[column].values, label=column)
		plt.legend(loc='center left', bbox_to_anchor=(1, 0.5))


def getAverageAgentOverTime():
	#print(df[df.columns[9:13]])
	print()
	x = df['Generation']
	for column in df.columns[9:13]:
		
		axes = plt.gca()
		axes.set_ylim([0,1])
		plt.subplot(223)
		plt.plot(x, df[column].values, label=column)
		plt.legend(loc='center left', bbox_to_anchor=(1, 0.5))

def getBestAgentsOverTime():
	#print(df[df.columns[9:13]])
	print()
	x = df['Generation']
	for column in df.columns[13:17]:
		
		axes = plt.gca()
		axes.set_ylim([0,1])
		plt.subplot(222)
		plt.plot(x, df[column].values, label=column)
		plt.legend(loc='center left', bbox_to_anchor=(1, 0.5))


realTime = True

if realTime:
	for i in range(1000):
		df = pd.read_csv("generationBPAWPAAAOutput.csv")
		getBestPerformingAgentOverTime()
		getWorstPerformingAgentOverTime()
		getAverageAgentOverTime()	
		getBestAgentsOverTime()
		plt.pause(2)
		plt.clf()
else:
	df = pd.read_csv("generationBPAWPAAAOutput.csv")
	getBestPerformingAgentOverTime()
	getWorstPerformingAgentOverTime()
	getAverageAgentOverTime()	
	getBestAgentsOverTime()

plt.show()