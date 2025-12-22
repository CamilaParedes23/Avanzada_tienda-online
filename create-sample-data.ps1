# Script para crear datos de prueba en la tienda de ropa

Write-Host "🚀 Creando datos de prueba para la tienda de ropa..." -ForegroundColor Green

# URL base
$baseUrl = "http://localhost:8080"

# Función para hacer POST requests
function New-ApiRequest {
    param(
        [string]$Endpoint,
        [string]$Body
    )
    try {
        $response = Invoke-WebRequest -Uri "$baseUrl$Endpoint" -Method POST -Body $Body -ContentType "application/json" -UseBasicParsing
        Write-Host "✅ $Endpoint - Status: $($response.StatusCode)" -ForegroundColor Green
        return $response
    }
    catch {
        Write-Host "❌ Error en $Endpoint`: $($_.Exception.Message)" -ForegroundColor Red
        return $null
    }
}

# Crear categorías adicionales
Write-Host "📁 Creando categorías..." -ForegroundColor Yellow

$pantalones = New-ApiRequest "/api/categorias" @'
{
  "nombre": "Pantalones",
  "descripcion": "Pantalones de vestir y casuales"
}
'@

$chaquetas = New-ApiRequest "/api/categorias" @'
{
  "nombre": "Chaquetas",
  "descripcion": "Chaquetas y abrigos para toda ocasión"
}
'@

$zapatos = New-ApiRequest "/api/categorias" @'
{
  "nombre": "Zapatos",
  "descripcion": "Calzado elegante y casual"
}
'@

# Crear productos
Write-Host "🛍️ Creando productos..." -ForegroundColor Yellow

# Productos de Camisas (categoría 1)
New-ApiRequest "/api/productos" @'
{
  "nombre": "Camisa Blanca Clásica",
  "descripcion": "Camisa blanca de algodón para oficina",
  "precio": 45.99,
  "stock": 25,
  "categoriaId": 1,
  "talla": "M",
  "color": "Blanco"
}
'@

New-ApiRequest "/api/productos" @'
{
  "nombre": "Camisa Azul Casual",
  "descripcion": "Camisa azul de algodón para uso casual",
  "precio": 39.99,
  "stock": 30,
  "categoriaId": 1,
  "talla": "L",
  "color": "Azul"
}
'@

# Productos de Pantalones (categoría 2)
New-ApiRequest "/api/productos" @'
{
  "nombre": "Pantalón Negro Formal",
  "descripcion": "Pantalón de vestir negro elegante",
  "precio": 89.99,
  "stock": 15,
  "categoriaId": 2,
  "talla": "32",
  "color": "Negro"
}
'@

New-ApiRequest "/api/productos" @'
{
  "nombre": "Jeans Azul Clásico",
  "descripcion": "Jeans de mezclilla azul resistente",
  "precio": 69.99,
  "stock": 20,
  "categoriaId": 2,
  "talla": "34",
  "color": "Azul"
}
'@

# Productos de Chaquetas (categoría 3)
New-ApiRequest "/api/productos" @'
{
  "nombre": "Chaqueta de Cuero",
  "descripcion": "Chaqueta de cuero genuino premium",
  "precio": 199.99,
  "stock": 8,
  "categoriaId": 3,
  "talla": "L",
  "color": "Marrón"
}
'@

# Productos de Zapatos (categoría 4)
New-ApiRequest "/api/productos" @'
{
  "nombre": "Zapatos Oxford",
  "descripcion": "Zapatos de cuero elegantes para oficina",
  "precio": 129.99,
  "stock": 12,
  "categoriaId": 4,
  "talla": "42",
  "color": "Marrón"
}
'@

# Verificar resultados
Write-Host "📊 Verificando datos creados..." -ForegroundColor Yellow

try {
    $categorias = Invoke-WebRequest -Uri "$baseUrl/api/categorias" -Method GET -UseBasicParsing
    $categoriasData = $categorias.Content | ConvertFrom-Json
    Write-Host "✅ Total categorías: $($categoriasData.Count)" -ForegroundColor Green

    $productos = Invoke-WebRequest -Uri "$baseUrl/api/productos" -Method GET -UseBasicParsing
    $productosData = $productos.Content | ConvertFrom-Json
    Write-Host "✅ Total productos: $($productosData.Count)" -ForegroundColor Green

    Write-Host "🎉 ¡Datos de prueba creados exitosamente!" -ForegroundColor Green
    Write-Host "🌐 El frontend ahora debería mostrar los productos correctamente" -ForegroundColor Cyan
}
catch {
    Write-Host "❌ Error al verificar datos: $($_.Exception.Message)" -ForegroundColor Red
}
