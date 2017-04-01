import json
with open('data.json', 'r') as infile:
    data = json.load(infile)

print(type(data["outputs"][0]["data"]["concepts"]))

for concept in data["outputs"][0]["data"]["concepts"]:
    name = concept["name"]
    print(name)
