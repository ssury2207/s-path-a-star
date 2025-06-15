# A* Algorithm – Pathfinding with Direction

## What is A*?

A* (A-star) is a pathfinding algorithm used to find the shortest route from a start point to a goal point. It improves upon Dijkstra's algorithm by using a heuristic to prioritize paths that seem more promising.

The algorithm evaluates each path using the formula:

f(n) = g(n) + h(n)

 
- `g(n)` is the actual cost from the start node to the current node.
- `h(n)` is the heuristic estimate of the cost from the current node to the goal.

---

## A* vs. Dijkstra

| Feature      | Dijkstra Algorithm         | A* Algorithm                        |
|--------------|----------------------------|-------------------------------------|
| Heuristic    | Not used                   | Yes (`h(n)` is used)                |
| Search Style | Explores all possibilities | Prioritizes paths toward the goal   |
| Efficiency   | Slower on large graphs     | Faster with a well-designed heuristic |
| Optimality   | Always finds the shortest path | Shortest path if `h(n)` is admissible |

---

## Understanding the Heuristic Function

The heuristic function `h(n)` is a way to estimate how far a node is from the goal. It helps the algorithm avoid unnecessary exploration by preferring paths that look closer to the destination.

A good heuristic:
- Never overestimates the true cost (admissible)
- Is fast to compute
- Guides the search effectively

---

## Designing a Heuristic

The right heuristic depends on the structure of your graph or map.

### 1. If nodes have coordinates (e.g., GPS locations)

Use **straight-line (great-circle)** distance between nodes.

- Appropriate for navigation and map-based applications
- Use the **Haversine formula** for accurate Earth-based distance

### 2. If using a 2D grid (e.g., game maps, mazes)

Choose based on movement rules:

| Movement Type              | Heuristic Formula                                 |
|----------------------------|---------------------------------------------------|
| Only up/down/left/right    | Manhattan Distance: `|x1 - x2| + |y1 - y2|`        |
| Diagonal movement allowed  | Chebyshev Distance: `max(|x1 - x2|, |y1 - y2|)`   |
| Diagonal with varied cost  | Euclidean Distance: `√[(x1 - x2)² + (y1 - y2)²]`   |

### 3. No coordinates or layout available

- Use `h(n) = 0` to fall back to Dijkstra’s behavior
- Or estimate based on expected steps/hops conservatively

---

## Example: Pathfinding in a Map Application

If building a map application like Google Maps:

- Each location (intersection, landmark) has GPS coordinates
- Roads are edges with real distances or time-based weights
- Use the **Haversine distance** as `h(n)` to estimate remaining distance to destination
- This ensures A* follows the most direct and realistic path first

---

## Heuristic Design Guidelines

| Situation                | Result                                      |
|--------------------------|---------------------------------------------|
| `h(n)` < true cost       | Safe (underestimating is acceptable)        |
| `h(n)` == true cost      | Ideal (most efficient performance)          |
| `h(n)` > true cost       | Risky (may skip the shortest path)          |

> Always ensure your heuristic is **admissible** (does not overestimate).

---

## When to Use A*

- Finding the shortest path on maps or navigation systems
- Game AI pathfinding (e.g., NPC movement, maze solving)
- Routing in graphs where efficiency and direction matter

---

## Summary

- A* combines actual cost and estimated cost to prioritize better paths.
- The heuristic helps focus the search in the right direction.
- It is more efficient than Dijkstra when a good heuristic is available.
- Choose or design your heuristic based on the problem’s structure.

