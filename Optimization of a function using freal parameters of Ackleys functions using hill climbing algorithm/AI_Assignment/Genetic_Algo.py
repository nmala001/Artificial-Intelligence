# Import randint from the random module
from random import randint

# Creates a array I just use for convenience
board = [1, 2, 3, 4, 5, 6, 7, 8,9,10,11,12,13,14,15,16]

count = 0
##################################################################
# FUNCTIONS
n = input("Enter the number of queens:")
for i in range (0,100):
# Function to check amount of attacking queens
    def check(array):
        # Collisions represent number of attacking queens, starts at zero obv.
        collisions = 0

        for i in range(1, int(n)):
            if i not in array:
                print ("DUPLICATE NUMBER ERROR - KILLING STUPID GENE")  # Never happen
                return 0

        # Total Collisions
        # For each queen in the array
        for i in range(0, int(n)):
            col = 0
            # For every other queen in the array
            for j in range(0, int(n)):
                # avoids checking self vs self
                if i != j:

                    if abs(board[i] - board[j]) == abs(array[j] - array[i]):
                        # Sets a variable to tell the next part that a collision was detected
                        col = 1
            # If a collision was detected add one to collisions
            if col == 1:
                collisions += 1
        # Return 8-colllisions, so that 0 is bad (8 attacking queens) and 8 is good (no attacking queens)
        return int(n) - collisions


    # The reproduce function, takes two arguements, the two parents
    def reproduce(array1, array2):

        size = int(int(n)/2)
        baby = array1[0:size]
        for i in array2:
            if i not in baby:
                baby.append(i)
        if randint(0, 100) > 20:
            baby = mutate(baby)
        population.append(baby)
        fitness.append(check(baby))

        baby = array2[0:size]
        for i in array1:
            if i not in baby:
                baby.append(i)
        if randint(0, 100) > 20:
            baby = mutate(baby)
        population.append(baby)
        fitness.append(check(baby))


    # Mutate the array given to the function
    def mutate(array1):
        # Chooses two random places (WARNING CAN BE SAME POSITION)
        a = randint(0, int(n)-1)
        b = randint(0, int(n)-1)
        # Swaps the two over (temporary var must be used (c))
        c = array1[a]
        array1[a] = array1[b]
        array1[b] = c
        return array1


    # Prints the population and corresponding fitness to the screen
    # Only used for debugging
    def printpop():
        # Prints the group
        for i in range(0, len(population)):
            print
            population[i], fitness[i]


    ##################################################################
    # VARIABLES
    popsize = 80

    variation = [4, 14]
    die = 0.1
    kill_limit = die * popsize

    ##################################################################
    # MAIN

    population = []
    fitness = []
    for i in range(0, popsize):
        population.append([1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15,16])
        a = 0
        while a < randint(variation[0], variation[1]):
            a += 1
            population[i] = mutate(population[i])
        fitness.append(check(population[i]))

    maxi = 0
    generations = 1

    while maxi != int(n):

        # Picks top # in group to be parents, kills rest
        killed = 0
        # starting at the lowest fitness (0 and increasing untill kill limit reached)
        x = 0
        while killed < kill_limit:
            for i in range(0, popsize):
                # Try is here to catch any errors
                try:
                    if fitness[i] == x:
                        # This bit removes the crappy gene from the population and from fitness
                        population.pop(i)
                        fitness.pop(i)
                        # increases the kill counter
                        killed += 1
                    if killed == kill_limit:
                        break
                except:
                    break
            # increments fitness
            x += 1

        babies = 0
        cpop = len(population) - 1  # current population
        while babies < killed:
            # produces two babies from two random parents (should prob give fittest parents preference)
            reproduce(population[cpop], population[ cpop])
            babies += 2


        generations += 1
        count = count +1

        # Looks for highest fitness in the group and checks if any have won!
        maxi = 0
        for i in range(0, popsize):
            if fitness[i] > maxi:
                maxi = fitness[i]

                if maxi == 8:
                    print
                    population[i]
                    #count = count + 1
                    break
    print ("Took",generations,"generations")

#print(count)