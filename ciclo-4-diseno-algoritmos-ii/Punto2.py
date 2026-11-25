import networkx as nx
import matplotlib.pyplot as plt

# 1. Crear el grafo y datos base
G = nx.Graph()

# Definimos las conexiones necesarias para conectar las 4 ciudades
mapa = [
    # Zona Norte/Oeste
    ('Coruña', 'Valladolid', 455), ('Vigo', 'Valladolid', 356),
    ('Valladolid', 'Madrid', 193), 
    
    # Zona Centro/Este (La entrada desde Barcelona)
    ('Barcelona', 'Valencia', 349), ('Valencia', 'Madrid', 191),
    ('Barcelona', 'Zaragoza', 296), ('Zaragoza', 'Madrid', 325), # Incluimos Zaragoza visualmente
    
    # Zona Sur/Oeste
    ('Madrid', 'Badajoz', 403),
    ('Madrid', 'Jaén', 335), ('Jaén', 'Sevilla', 242),
    ('Sevilla', 'Cádiz', 125)
]

G.add_weighted_edges_from(mapa)

# 2. Definimos las 4 Rutas a Comparar
# Nota: Usamos la ruta más óptima (Vía Valencia) para todas porque es la más rápida para cruzar
ruta_cadiz = [('Barcelona', 'Valencia'), ('Valencia', 'Madrid'), ('Madrid', 'Jaén'), ('Jaén', 'Sevilla'), ('Sevilla', 'Cádiz')]
ruta_coruna = [('Barcelona', 'Valencia'), ('Valencia', 'Madrid'), ('Madrid', 'Valladolid'), ('Valladolid', 'Coruña')]
ruta_vigo = [('Barcelona', 'Valencia'), ('Valencia', 'Madrid'), ('Madrid', 'Valladolid'), ('Valladolid', 'Vigo')]
ruta_badajoz = [('Barcelona', 'Valencia'), ('Valencia', 'Madrid'), ('Madrid', 'Badajoz')]

# 3. Posiciones (Layout del mapa)
pos = {
    'Coruña': (1, 8), 'Vigo': (1, 7), 
    'Valladolid': (3, 6), 'Zaragoza': (6, 7), 
    'Barcelona': (9, 6), 'Madrid': (5, 5), 
    'Badajoz': (2, 4), 'Valencia': (7, 4), 
    'Jaén': (4, 3), 'Sevilla': (2, 2), 'Cádiz': (1, 1)
}

plt.figure(figsize=(14, 9))

# --- CAPA 1: Fondo (Mapa Base) ---
nx.draw_networkx_nodes(G, pos, node_size=500, node_color='lightgray')
nx.draw_networkx_labels(G, pos, font_size=8)
nx.draw_networkx_edges(G, pos, edge_color='#E0E0E0', width=1)

# --- CAPA 2: Las Rutas Competidoras ---
# Dibujamos primero las más gruesas/importantes al fondo para que se vean los desvíos

# Competidor 1: Coruña (Azul) - 1188 km
nx.draw_networkx_edges(G, pos, edgelist=ruta_coruna, edge_color='#1976D2', width=6, alpha=0.4, label='A Coruña (1188 km)')

# Competidor 2: Vigo (Verde) - 1089 km
# Usamos "style='dashed'" para diferenciarla de Coruña ya que comparten camino
nx.draw_networkx_edges(G, pos, edgelist=ruta_vigo, edge_color='#388E3C', width=3, style='dashed', label='A Vigo (1089 km)')

# Competidor 3: Badajoz (Naranja) - 943 km
nx.draw_networkx_edges(G, pos, edgelist=ruta_badajoz, edge_color='#FBC02D', width=3, label='A Badajoz (943 km)')

# --- CAPA 3: La Ganadora (Cádiz) ---
# La dibujamos al final, en Rojo Intenso, para que resalte sobre las demás
nx.draw_networkx_edges(G, pos, edgelist=ruta_cadiz, edge_color='#D32F2F', width=2, label='GANADOR: A Cádiz (1242 km)')

# Destacar los nodos finales (Las 4 ciudades destino)
destinos = ['Cádiz', 'Coruña', 'Vigo', 'Badajoz']
nx.draw_networkx_nodes(G, pos, nodelist=destinos, node_color=['red', 'blue', 'green', 'orange'], node_size=800)

plt.title("Comparativa: Ciudades más lejanas desde Barcelona")
plt.legend(loc='lower left', fontsize=10)
plt.axis('off')

print("Generando gráfico comparativo...")
plt.show()
