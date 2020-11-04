import pandas as pd
import matplotlib.pyplot as plt
plt.style.use('ggplot')

fig, axs = plt.subplots(3, 1, constrained_layout=True)

def fillSubPlot(subPlotIndex, labelOffset, fromColumn, toColumn, title):
	x = df['Generation']
	ax = axs[subPlotIndex]
	for column in df.columns[fromColumn:toColumn]:

		ax.set_ylim([-0.05,1.05])
		ax.set_title(title)
		ax.plot(x, df[column].values, label=column[labelOffset:])
		ax.legend(loc='center left', bbox_to_anchor=(1, 0.5))


def getBestPerformingAgentOverTime():
	x = df['Generation']
	for column in df.columns[1:5]:

		axs[0].set_ylim([-0.05,1.05])
		axs[0].set_title("Best Performing Agent")
		axs[0].plot(x, df[column].values, label=column[5:])
		axs[0].legend(loc='center left', bbox_to_anchor=(1, 0.5))


def getWorstPerformingAgentOverTime():
	x = df['Generation']
	for column in df.columns[5:9]:

		axs[1].set_ylim([-0.05,1.05])
		axs[1].set_title("Worst Performing Agent")
		axs[1].plot(x, df[column].values, label=column[5:])
		axs[1].legend(loc='center left', bbox_to_anchor=(1, 0.5))


def getAverageAgentOverTime():
	x = df['Generation']
	for column in df.columns[9:13]:
		
		axs[2].set_ylim([-0.05,1.05])
		axs[2].set_title("Average of All Agents")
		axs[2].plot(x, df[column].values, label=column[4:])
		axs[2].legend(loc='center left', bbox_to_anchor=(1, 0.5))

realTime = False
fileCode = input("Enter file: ")
fileName = "{}/generationDetailsOutput.csv".format(fileCode)


if realTime:
	for i in range(1000):
		df = pd.read_csv(fileName)
		fillSubPlot(0, 5, 1, 5, "Best Performing Agent")
		fillSubPlot(1, 5, 5, 9, "Worst Performing Agent")
		fillSubPlot(2, 4, 9, 13, "Average of All Agents")
		plt.pause(0.5)
		plt.clf()
else:
	df = pd.read_csv(fileName)
	fillSubPlot(0, 5, 1, 5, "Best Performing Agent")
	fillSubPlot(1, 5, 5, 9, "Worst Performing Agent")
	fillSubPlot(2, 4, 9, 13, "Average of All Agents")

plt.show()