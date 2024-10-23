import pygame
import math
import matplotlib.pyplot as plt
import numpy as np
import cv2

frames = []
with open('input.txt') as f:
    num_frames = int(f.readline())
    for _ in range(num_frames):
        green_circles = []
        red_circles = []
        num_green_circles = int(f.readline())
        for _ in range(num_green_circles):
            x, y, direction = map(float, f.readline().split())
            green_circles.append((x / 128 * 800, y / 128 * 800, direction))
        num_red_circles = int(f.readline())
        for _ in range(num_red_circles):
            x, y, direction = map(float, f.readline().split())
            red_circles.append((x / 128 * 800, y / 128 * 800, direction))
        frames.append({"green": green_circles, "red": red_circles})

pygame.init()
screen = pygame.display.set_mode((800, 800))
clock = pygame.time.Clock()

fourcc = cv2.VideoWriter_fourcc(*'mp4v')  # Use 'XVID' for AVI format
video_width, video_height = 800, 800
video_writer = cv2.VideoWriter('output.mp4', fourcc, 120, (video_width, video_height))  # Adjusted frame rate to 120

green_data = []
red_data = []
for frame_index in range(num_frames):
    green_circles = frames[frame_index]["green"]
    red_circles = frames[frame_index]["red"]

    green_circles_count = len(green_circles)
    red_circles_count = len(red_circles)

    green_data.append((frame_index, green_circles_count))
    red_data.append((frame_index, red_circles_count))

green_data = np.array(green_data)
red_data = np.array(red_data)

plt.figure(figsize=(10, 5))
plt.xlabel('Frame Index')
plt.ylabel('Number of Circles')
plt.title('Number of Green and Red Circles over Time')

frame_rate = 120  
finished = False
frame_index = 0
num_frames = len(frames)
while not finished:
    for event in pygame.event.get():
        if event.type == pygame.QUIT:
            finished = True

    screen.fill((255, 255, 255))

    green_circles = frames[frame_index]["green"]
    red_circles = frames[frame_index]["red"]

    for circle in green_circles:
        x, y, direction = circle
        dx, dy = math.cos(direction), math.sin(direction)
        points = [(x + dx * 10, y + dy * 10), (x + dy * 5, y - dx * 5), (x - dy * 5, y + dx * 5)]
        pygame.draw.polygon(screen, (0, 255, 0), points)

    for circle in red_circles:
        x, y, direction = circle
        dx, dy = math.cos(direction), math.sin(direction)
        points = [(x + dx * 10, y + dy * 10), (x + dy * 5, y - dx * 5), (x - dy * 5, y + dx * 5)]
        pygame.draw.polygon(screen, (255, 0, 0), points)

    pygame_image = pygame.surfarray.array3d(screen)
    opencv_image = cv2.cvtColor(pygame_image, cv2.COLOR_RGB2BGR)

    video_writer.write(opencv_image)

    pygame.display.flip()
    frame_index = (frame_index + 1) % num_frames
    clock.tick(frame_rate)

pygame.quit()
video_writer.release()

plt.scatter(green_data[:, 0], green_data[:, 1], color='green', label='Green Circles', s=1)
plt.scatter(red_data[:, 0], red_data[:, 1], color='red', label='Red Circles', s=1)
plt.legend()
plt.show()
