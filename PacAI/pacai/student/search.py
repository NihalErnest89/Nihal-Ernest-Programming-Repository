"""
In this file, you will implement generic search algorithms which are called by Pacman agents.
"""

from pacai.util.priorityQueue import PriorityQueue
from pacai.util.stack import Stack
from pacai.util.queue import Queue


def depthFirstSearch(problem):
    """
    Search the deepest nodes in the search tree first [p 85].

    Your search algorithm needs to return a list of actions that reaches the goal.
    Make sure to implement a graph search algorithm [Fig. 3.7].

    To get started, you might want to try some of these simple commands to
    understand the search problem that is being passed in:
    ```
    print("Start: %s" % (str(problem.startingState())))
    print("Is the start a goal?: %s" % (problem.isGoal(problem.startingState())))
    print("Start's successors: %s" % (problem.successorStates(problem.startingState())))
    ```
    """

    # *** Your Code Here ***
    print("Start: %s" % (str(problem.startingState())))
    print("Is the start a goal?: %s" % (problem.isGoal(problem.startingState())))
    print("Start's successors: %s" % (problem.successorStates(problem.startingState())))

    print("")

    visited = []
    stack = Stack()
    parents = {}

    stack.push((problem.startingState(), "Undefined"))

    goalState = problem.startingState()

    while not stack.isEmpty():
        currentNode = stack.pop()
        currentState = currentNode[0]

        if problem.isGoal(currentState):
            goalState = currentNode
            break
        
        if currentState not in visited:
            visited.append(currentState)

        for x in problem.successorStates(currentState):
            if (x[0] not in visited):
                stack.push(x)
                parents[x] = currentNode

    directions = []

    while (goalState[1] != "Undefined"):
        directions.insert(0, goalState[1])
        goalState = parents[goalState]

    return directions

def breadthFirstSearch(problem):
    """
    Search the shallowest nodes in the search tree first. [p 81]
    """
    # *** Your Code Here ***

    visited = []
    queue = Queue()
    parents = {}

    queue.push((problem.startingState(), "Undefined"))

    goalState = problem.startingState()

    while not queue.isEmpty():
        currentNode = queue.pop()
        currentState = currentNode[0]

        if problem.isGoal(currentState):
            # print("goal state found")
            goalState = currentNode
            break
        
        if currentState not in visited:
            visited.append(currentState)

        for x in problem.successorStates(currentState):
            if (x[0] not in visited):
                queue.push(x)
                parents[x] = currentNode

    directions = []

    while (goalState[1] != "Undefined"):
        directions.insert(0, goalState[1])
        goalState = parents[goalState]

    return directions

def uniformCostSearch(problem):
    """
    Search the node of least total cost first.
    """

    # *** Your Code Here ***

    visited = []
    queue = PriorityQueue()
    parents = {}

    start = problem.startingState()

    queue.push((start, "Undefined", 0), 0)

    cost = {}

    cost[start] = 0

    goalState = problem.startingState()
    goal = False

    while not queue.isEmpty() and not goal:
        currentNode = queue.pop()
        currentState = currentNode[0]

        if problem.isGoal(currentState):
            goalState = currentNode
            goal = True
            break
        
        if currentState not in visited:
            visited.append(currentState)

        for x in problem.successorStates(currentState):
            if (x[0] not in visited):
                p = currentNode[2] + x[2]

                if (x[0] in cost):
                    if (cost[x[0]] <= p):
                        continue

                queue.push((x[0], x[1], p), p)
                cost[x[0]] = p
                parents[(x[0], x[1])] = currentNode

    directions = []

    while (goalState[1] != "Undefined"):
        directions.insert(0, goalState[1])
        goalState = parents[(goalState[0], goalState[1])]

    return directions

def aStarSearch(problem, heuristic):
    """
    Search the node that has the lowest combined cost and heuristic first.
    """

    # *** Your Code Here ***

    print(f'Heuristic: {heuristic}')
    
    visited = []
    queue = PriorityQueue()
    parents = {}

    start = problem.startingState()

    queue.push((start, "Undefined", 0), 0)

    cost = {}

    cost[start] = 0

    goalState = problem.startingState()
    goal = False

    while not queue.isEmpty() and not goal:
        currentNode = queue.pop()
        currentState = currentNode[0]

        if problem.isGoal(currentState):
            goalState = currentNode
            goal = True
            break
        
        if currentState not in visited:
            visited.append(currentState)

        for x in problem.successorStates(currentState):
            if (x[0] not in visited):
                p = currentNode[2] + x[2] + heuristic(x[0], problem)

                if (x[0] in cost):
                    if (cost[x[0]] <= p):
                        continue

                queue.push((x[0], x[1], p), p)
                cost[x[0]] = p
                parents[(x[0], x[1])] = currentNode

    directions = []

    while (goalState[1] != "Undefined"):
        directions.insert(0, goalState[1])
        goalState = parents[(goalState[0], goalState[1])]

    return directions
