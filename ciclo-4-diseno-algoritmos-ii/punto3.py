import networkx as nx
import matplotlib.pyplot as plt

G = nx.Graph()

# Usamos las mismas conexiones básicas
mapa = [
    ('Sevilla', 'Jaén', 242), ('Sevilla', 'Granada', 256),
    ('Jaén', 'Madrid', 335), ('Granada', 'Murcia', 278),
    ('Granada', 'Jaén', 99), ('Murcia', 'Albacete', 150),
    ('Murcia', 'Valencia', 241), ('Albacete', 'Madrid', 251),
    ('Valencia', 'Madrid', 191), ('Valencia', 'Barcelona', 349),
    ('Madrid', 'Zaragoza', 325), ('Barcelona', 'Zaragoza', 296),
    ('Zaragoza', 'Bilbao', 324) 
]
G.add_weighted_edges_from(mapa)

pos = {
    'Zaragoza': (6, 7), 'Barcelona': (9, 6), 'Madrid': (5, 5),
    'Valencia': (7, 4), 'Albacete': (6, 3), 'Murcia': (7, 2),
    'Jaén': (4, 3), 'Granada': (5, 1), 'Sevilla': (2, 2), 'Bilbao':(5,8)
}

plt.figure(figsize=(12, 8))

# 1. Ruta Directa (Azul - Por el centro)
ruta_central = [('Sevilla', 'Jaén'), ('Jaén', 'Madrid'), ('Madrid', 'Zaragoza')]

# 2. Ruta Turística Sur (Verde - Dando vuelta por Murcia/Albacete)
ruta_sur = [('Sevilla', 'Granada'), ('Granada', 'Murcia'), ('Murcia', 'Albacete'), ('Albacete', 'Madrid'), ('Madrid', 'Zaragoza')]

# 3. Ruta Costera (Naranja - Por Valencia/Barcelona)
ruta_costa = [('Sevilla', 'Granada'), ('Granada', 'Murcia'), ('Murcia', 'Valencia'), ('Valencia', 'Barcelona'), ('Barcelona', 'Zaragoza')]

# Dibujar nodos y aristas base
nx.draw_networkx_nodes(G, pos, node_size=600, node_color='white', edgecolors='black')
nx.draw_networkx_labels(G, pos)
nx.draw_networkx_edges(G, pos, edge_color='#EEEEEE', width=1)

# Dibujar las 3 rutas superpuestas
# Usamos 'offset' visual haciendo unas líneas más gordas que otras para que se vean si se solapan
nx.draw_networkx_edges(G, pos, edgelist=ruta_costa, edge_color='orange', width=6, alpha=0.5, label='Ruta Costera (Larga)')
nx.draw_networkx_edges(G, pos, edgelist=ruta_sur, edge_color='green', width=4, style='dashed', label='Ruta Sur-Interior')
nx.draw_networkx_edges(G, pos, edgelist=ruta_central, edge_color='blue', width=2, label='Ruta Central (Rápida)')

plt.title("Distintos Caminos: Sevilla -> Zaragoza")
plt.legend(loc='lower right')
plt.axis('off')
plt.show()