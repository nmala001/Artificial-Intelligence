
import csv
import sys
import getopt
import random
from random import randint
import numpy as np
a=[]
for i in range(0,100):

    count=0
    x = random.randint(-5,5)
    y = random.randint(-5,5)
    flag=0
    mini=999999999
    while(flag==0):
        # random numbers - check it once\par
        # cross check these two eq ,as per ackleys function  \par
        part1 = -0.2 * np.sqrt(0.5 * (x ** 2 + y ** 2))
        part2 = 0.5 * (np.cos(2 * np.pi * x) + np.cos(2 * np.pi * y))
        #out put of ackleys\par
        ackleysOP = -20 * np.exp(part1) - np.exp(part2)
    
        if mini > ackleysOP:
            mini = ackleysOP
            count=0
        else:
            count+=1
        ran = (random.randint(0,99))/100.0
            #print ran
        xnew = (ran -  0.5) * 0.1 + x
        ynew = (ran -  0.5) * 0.1 + y
        if count==100:
            a1=[]
            a1.append(xnew)
            a1.append(ynew)
            a1.append(mini)
            a.append(a1)
            flag=1
        x=xnew
        y=ynew

f = open('output.csv', 'wt')

writer = csv.writer(f)
writer.writerow( ('x', 'y', 'Local Minimum') )
for i in range(0,100):
    print a[i]
    
    writer.writerow( (a[i][0], a[i][1], a[i][2]) )

f.close()
