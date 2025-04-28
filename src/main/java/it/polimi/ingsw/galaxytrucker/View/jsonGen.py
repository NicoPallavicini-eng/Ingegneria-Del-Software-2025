import json

# Load your file
file_path = 'C:\Users\elisa\OneDrive\Documenti\Elisa\Università\IDS\Progetto_IDS\src\main\java\it\polimi\ingsw\galaxytrucker\View\graphics'  # (adjust if needed)

with open(file_path, 'r', encoding='utf-8') as f:
    lines = f.readlines()

# 1. Split legend and real tiles
legend_lines = lines[:87]
tile_lines = lines[103:]  # <-- Start from line 103 onwards

# 2. Build the rotation map from the legend
def build_rotation_map(legend_lines):
    rotation_map = {}
    for line in legend_lines:
        parts = line.strip().split()
        if len(parts) >= 5:
            symbol = parts[0]
            rotation_map[symbol] = {
                'N': parts[1],
                'E': parts[2],
                'S': parts[3],
                'W': parts[4]
            }
    return rotation_map

rotation_map = build_rotation_map(legend_lines)

# 3. Direction helpers
def rotate_direction(start_dir, degrees):
    dirs = ['N', 'E', 'S', 'W']
    idx = dirs.index(start_dir)
    steps = degrees // 90
    return dirs[(idx + steps) % 4]

def rotate_symbol(symbol, from_dir, to_dir, rotation_map):
    # simple version for now, can be expanded if needed
    return symbol

# 4. Parse a tile into JSON format
def parse_tile(tile_lines, rotation_map):
    static = {
        "CornerNE": tile_lines[0][0],
        "CornerSE": tile_lines[4][0],
        "CornerSW": tile_lines[4][-1],
        "CornerNW": tile_lines[0][-1],
        "BorderH": tile_lines[0][1:-1].strip(),
        "BorderV": tile_lines[1][0],
        "Center": tile_lines[2][3:6].strip()
    }

    dynamic_r0 = {
        "ConnectorN": tile_lines[0][3:6],
        "ConnectorE": tile_lines[2][6],
        "ConnectorS": tile_lines[4][3:6],
        "ConnectorW": tile_lines[2][0]
    }

    dynamic = {"R0": dynamic_r0}

    for rotation in [90, 180, 270]:
        rotated = {}
        for conn_dir, conn_value in dynamic_r0.items():
            rotated[f"Connector{rotate_direction(conn_dir[-1], rotation)}"] = conn_value
        dynamic[f"R{rotation}"] = rotated

    return {"static": static, "dynamic": dynamic}

# 5. Find the first tile
first_tile_start = None
for idx, line in enumerate(tile_lines):
    if line.strip().startswith('╭'):
        first_tile_start = idx
        break

# 6. Extract and parse the tile
first_tile_lines = tile_lines[first_tile_start:first_tile_start + 5]
first_tile_lines = [line.rstrip('\n') for line in first_tile_lines]

parsed_first_tile = parse_tile(first_tile_lines, rotation_map)

# 7. Save to JSON
output = {
    "connector1": parsed_first_tile
}

with open('/mnt/data/parsed_tile.json', 'w', encoding='utf-8') as f:
    json.dump(output, f, indent=4, ensure_ascii=False)

print("✅ First tile parsed and saved as parsed_tile.json!")