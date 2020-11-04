import pandas as pd
import matplotlib.pyplot as plt
plt.style.use('ggplot')

fig, axs = plt.subplots(3, 2, constrained_layout=True)

def fillSubPlot(subPlotI, subPlotJ, labelOffset, fromColumn, toColumn, title, setYlim):
	x = df['Generation']
	ax = axs[subPlotI, subPlotJ]
	for column in df.columns[fromColumn:toColumn]:

		if setYlim:
			ax.set_ylim([-0.05,1.05])
		ax.set_title(title)
		ax.plot(x, df[column].values, label=column[labelOffset:])
		ax.legend(loc='center left', bbox_to_anchor=(1, 0.5))

fileCode = input("Enter file: ")
fileName = "{}/generationDetailsOutput.csv".format(fileCode)

df = pd.read_csv(fileName)
fillSubPlot(0, 0, 5, 1, 5, "Best Performing Agent", True)
fillSubPlot(1, 0, 4, 5, 9, "Standard Deviation", True)
fillSubPlot(2, 0, 4, 9, 13, "Average of All Agents", True)
fillSubPlot(0, 1, 6, 13, 17, "Average Top (k) Agents", True)
fillSubPlot(1, 1, 20, 17, 18, "Total Score", False)

#fill sub plot with score

plt.show()