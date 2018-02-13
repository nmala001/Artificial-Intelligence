import random
import copy
import math
file=open("differential_output.txt","w")

NP = 10
NC = 100
F = 0.9
CR = 0.15
duplicates=[]
dictionary={}
newdictionary={}
newoutput=0
oldoutput=0


def Differential_Value(x,y):

    output = (-20)*math.exp(-0.2*math.sqrt(0.5*((x*x)+(y*y)))) - math.exp(0.5*(math.cos(math.radians(2*180*x))+math.cos(math.radians(2*180*y))))+20+math.e
    return output

for i in range(0,100):
    list1 = []
    for j in range (0,NP):
        x = random.uniform(-5,5)
        y = random.uniform(-5,5)

        newdictionary[j] = (x,y)

    for k in range(0,NC):

         dictionary=newdictionary.copy()
         for i in range(0,NP):
            s=random.sample(range(0,NP),3)
            while True:
                if i not in s:
                    duplicates=s
                    break
                else:
                    s=random.sample(range(0,NP),3)

            Vx = dictionary[duplicates[0]][0]+(F*(dictionary[duplicates[1]][0]-dictionary[duplicates[2]][0]))
            Vy = dictionary[duplicates[0]][1] + (F * (dictionary[duplicates[1]][1] - dictionary[duplicates[2]][1]))

            U=random.uniform(0,1)
            if(U<CR):
                Ux=Vx
            else:
                Ux=dictionary[i][0]
            U=random.uniform(0,1)
            if(U<CR):
                Uy=Vy
            else:
                Uy=dictionary[i][1]
                newoutput=Differential_Value(Ux,Uy)

            oldoutput=Differential_Value(dictionary[i][0],dictionary[i][1])
            if(newoutput<oldoutput):
                newdictionary[i]=(Ux,Uy)
            else:
                newdictionary[i]=(dictionary[i][0],dictionary[i][1])


    for i in range (0,10):
        list1.append(Differential_Value(newdictionary[i][0],newdictionary[i][1]))

        finallist=min(list1)
        #globalminimum=min(finallist)

        file.write("\n" + str(finallist))
        print (finallist)

file.close()
















