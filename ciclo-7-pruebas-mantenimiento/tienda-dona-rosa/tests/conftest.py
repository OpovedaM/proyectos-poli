import pytest
import database


@pytest.fixture
def bd_temporal(tmp_path, monkeypatch):
    """Crea una base de datos de mentira, con los 10 productos iniciales."""
    monkeypatch.setattr(database, "NOMBRE_BD", str(tmp_path / "prueba.db"))
    database.crear_tabla()
    database.cargar_datos_iniciales()