import networkx as nx
import matplotlib.pyplot as plt

# 1. Crear el grafo
G = nx.Graph()

# 2. Datos: (Origen, Destino, Distancia)
# Incluimos las rutas que calculamos en el punto 1
aristas = [
    # Ruta Ganadora (Verde)
    ('Murcia', 'Albacete', 150),
    ('Albacete', 'Madrid', 251),
    ('Madrid', 'Badajoz', 403),
    
    # Ruta Alternativa (Naranja)
    ('Murcia', 'Valencia', 241),
    ('Valencia', 'Madrid', 191),
    
    # Ruta Larga (Roja)
    ('Murcia', 'Granada', 278),
    ('Granada', 'Jaén', 99),
    ('Jaén', 'Madrid', 335)
]

# Añadir las líneas al grafo
G.add_weighted_edges_from(aristas)

# 3. Posiciones manuales (Simulando el mapa de España)
pos = {
    'Badajoz': (0, 5),
    'Madrid': (4, 5),
    'Albacete': (5, 3),
    'Valencia': (7, 4),
    'Murcia': (6, 2),
    'Jaén': (3, 2),
    'Granada': (4, 1)
}

# 4. Configurar el dibujo
plt.figure(figsize=(10, 6)) # Tamaño de la ventana

# Dibujar nodos (bolitas)
nx.draw_networkx_nodes(G, pos, node_size=800, node_color='#A0CBE2')
nx.draw_networkx_labels(G, pos, font_size=10, font_weight='bold')

# Definir grupos de aristas para colorear
ruta_optima = [('Murcia', 'Albacete'), ('Albacete', 'Madrid'), ('Madrid', 'Badajoz')]
ruta_valencia = [('Murcia', 'Valencia'), ('Valencia', 'Madrid')]
ruta_sur = [('Murcia', 'Granada'), ('Granada', 'Jaén'), ('Jaén', 'Madrid')]

# Dibujar las líneas con colores
nx.draw_networkx_edges(G, pos, edgelist=ruta_sur, edge_color='red', width=2, style='dashed', label='Ruta Sur (Larga)')
nx.draw_networkx_edges(G, pos, edgelist=ruta_valencia, edge_color='orange', width=2, style='dashed', label='Ruta Valencia')
nx.draw_networkx_edges(G, pos, edgelist=ruta_optima, edge_color='green', width=4, label='Ruta Óptima (Corta)')

# Dibujar los números de distancia
etiquetas_distancia = nx.get_edge_attributes(G, 'weight')
nx.draw_networkx_edge_labels(G, pos, edge_labels=etiquetas_distancia)

plt.title("Ruta más corta: Murcia a Badajoz (Algoritmo Dijkstra)")
plt.legend(loc='lower left')
plt.axis('off') # Quitar el marco cuadrado

# Muestra la ventana
print("Generando gráfico...")
plt.show()