import matplotlib.pyplot as plt
import numpy as np

frames = []
with open('input.txt') as f:
    num_frames = int(f.readline())
    for _ in range(num_frames):
        prey = []
        predator = []
        num_prey = int(f.readline())
        for _ in range(num_prey):
            x, y, direction = map(float, f.readline().split())
            prey.append((x / 128 * 800, y / 128 * 800, direction))
        num_predator = int(f.readline())
        for _ in range(num_predator):
            x, y, direction = map(float, f.readline().split())
            predator.append((x / 128 * 800, y / 128 * 800, direction))
        frames.append({"green": prey, "red": predator})

green_data = []
red_data = []
for frame_index in range(num_frames):
    prey = frames[frame_index]["green"]
    predator = frames[frame_index]["red"]

    prey_count = len(prey)
    predator_count = len(predator)

    green_data.append((frame_index, prey_count))
    red_data.append((frame_index, predator_count))

green_data = np.array(green_data)
red_data = np.array(red_data)

plt.figure(figsize=(10, 5))
plt.xlabel('Frame Index')
plt.ylabel('Number of Creatures')
plt.title('Number of Preys and Predators over Time')

plt.scatter(green_data[:, 0], green_data[:, 1], color='green', label='Preys', s=1)
plt.scatter(red_data[:, 0], red_data[:, 1], color='red', label='Predators', s=1)
plt.legend()
plt.show()
