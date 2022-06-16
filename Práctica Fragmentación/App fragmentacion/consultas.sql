-- Listar al empleado que atendio mas ordenes
SELECT SalesPersonID, COUNT(Sales.SalesOrderHeader.TerritoryID) as numeroOrdenesAtendidas
FROM Sales.SalesOrderHeader INNER JOIN Sales.SalesPerson
ON Sales.SalesOrderHeader.SalesPersonID = Sales.SalesPerson.BusinessEntityID
GROUP BY SalesPersonID ORDER BY COUNT(*) DESC

CREATE OR ALTER PROCEDURE sp_listarOrdenesEmpleados
as begin 
	BEGIN TRY
		BEGIN TRANSACTION
	SELECT SalesPersonID, COUNT(Sales.SalesOrderHeader.TerritoryID) as numeroOrdenesAtendidas
	FROM Sales.SalesOrderHeader INNER JOIN Sales.SalesPerson
	ON Sales.SalesOrderHeader.SalesPersonID = Sales.SalesPerson.BusinessEntityID
	GROUP BY SalesPersonID ORDER BY COUNT(*) DESC
		COMMIT TRANSACTION
	END TRY 
	BEGIN CATCH   
		ROLLBACK TRANSACTION   
		RAISERROR ('No se pudo realizar la accion',16,1)  
	END CATCH
end
go

exec sp_listarOrdenesEmpleados
go

-- Buscar cuantas ordenes atendio un cierto empleado
SELECT SalesPersonID, COUNT(Sales.SalesOrderHeader.TerritoryID) as numeroOrdenesAtendidas
FROM Sales.SalesOrderHeader INNER JOIN Sales.SalesPerson
ON Sales.SalesOrderHeader.SalesPersonID = Sales.SalesPerson.BusinessEntityID
WHERE SalesPersonID = 278
GROUP BY SalesPersonID ORDER BY COUNT(*) DESC

CREATE OR ALTER PROCEDURE sp_listarOrdenesEmpleadosID @SalesPersonID int
as begin 
	BEGIN TRY
		BEGIN TRANSACTION
    SELECT SalesPersonID, COUNT(Sales.SalesOrderHeader.TerritoryID) as numeroOrdenesAtendidas
    FROM Sales.SalesOrderHeader INNER JOIN Sales.SalesPerson
    ON Sales.SalesOrderHeader.SalesPersonID = Sales.SalesPerson.BusinessEntityID
    WHERE SalesPersonID = @SalesPersonID
    GROUP BY SalesPersonID ORDER BY COUNT(*) DESC
		COMMIT TRANSACTION
	END TRY 
	BEGIN CATCH   
		ROLLBACK TRANSACTION   
		RAISERROR ('No se pudo realizar la accion',16,1)  
	END CATCH
end
go

exec sp_listarOrdenesEmpleadosID 277
go


-- Listar los datos del cliente con m�s ordenes solicitadas en la regi�n "North America"
SELECT * FROM OPENQUERY (SERVIDOR3,'SELECT TOP 1 NorthAmerica.Sales.Customer.CustomerID, COUNT(NorthAmerica.sales.SalesOrderHeader.SalesOrderID) AS NumberOfOrders
FROM NorthAmerica.Sales.SalesOrderHeader INNER JOIN NorthAmerica.Sales.Customer 
ON NorthAmerica.Sales.SalesOrderHeader.CustomerID = NorthAmerica.Sales.Customer.CustomerID 
WHERE NorthAmerica.Sales.SalesOrderHeader.TerritoryID IN (1,2,3,4,5,6)
GROUP BY NorthAmerica.Sales.Customer.CustomerID ORDER BY NumberOfOrders DESC')

go
CREATE OR ALTER PROCEDURE sp_listarClienteNorthAmerica
as begin 
	BEGIN TRY
		BEGIN TRANSACTION
	SELECT * FROM OPENQUERY (SERVIDOR3,'SELECT TOP 1 NorthAmerica.Sales.Customer.CustomerID, COUNT(NorthAmerica.sales.SalesOrderHeader.SalesOrderID) AS NumberOfOrders
	FROM NorthAmerica.Sales.SalesOrderHeader INNER JOIN NorthAmerica.Sales.Customer 
	ON NorthAmerica.Sales.SalesOrderHeader.CustomerID = NorthAmerica.Sales.Customer.CustomerID 
	WHERE NorthAmerica.Sales.SalesOrderHeader.TerritoryID IN (1,2,3,4,5,6)
	GROUP BY NorthAmerica.Sales.Customer.CustomerID ORDER BY NumberOfOrders DESC')
		COMMIT TRANSACTION
	END TRY 
	BEGIN CATCH   
		ROLLBACK TRANSACTION   
		RAISERROR ('No se pudo realizar la accion',16,1)  
	END CATCH
end
go

exec sp_listarClienteNorthAmerica
go


-- 8.  Listar los productos que no est�n disponibles a la venta 
SELECT * FROM Production.Product
WHERE SellEndDate is not NULL


go
CREATE OR ALTER PROCEDURE sp_productosNoDisponibles
as begin 
	BEGIN TRY
		BEGIN TRANSACTION
	SELECT * FROM Production.Product
	WHERE SellEndDate is not NULL
		COMMIT TRANSACTION
	END TRY 
	BEGIN CATCH   
		ROLLBACK TRANSACTION   
		RAISERROR ('No se pudo realizar la accion',16,1)  
	END CATCH
end
go

exec sp_productosNoDisponibles

-- 5. Listar las ofertas que tienen los productos de la categor�a �Bikes�
go
CREATE OR ALTER PROCEDURE sp_ofertasProductoBikes
as begin 
	BEGIN TRY
		BEGIN TRANSACTION
	SELECT * FROM Sales.SpecialOffer WHERE SpecialOfferID IN (
	SELECT SpecialOfferID FROM Sales.SpecialOfferProduct SOP 
	INNER JOIN Production.Product P ON SOP.ProductID = P.ProductID
	INNER JOIN Production.ProductSubcategory PS ON P.ProductSubcategoryID = PS.ProductSubcategoryID
	INNER JOIN Production.ProductCategory PC ON PS.ProductCategoryID = PC.ProductCategoryID WHERE
	[PC].[Name] = 'Bikes');
		COMMIT TRANSACTION
	END TRY 
	BEGIN CATCH   
		ROLLBACK TRANSACTION   
		RAISERROR ('No se pudo realizar la accion',16,1)  
	END CATCH
end
go

exec sp_ofertasProductoBikes

SELECT * FROM Sales.SpecialOffer WHERE SpecialOfferID IN (
SELECT SpecialOfferID FROM Sales.SpecialOfferProduct SOP 
INNER JOIN Production.Product P ON SOP.ProductID = P.ProductID
INNER JOIN Production.ProductSubcategory PS ON P.ProductSubcategoryID = PS.ProductSubcategoryID
INNER JOIN Production.ProductCategory PC ON PS.ProductCategoryID = PC.ProductCategoryID WHERE
[PC].[Name] = 'Bikes');
-- 6. Listar los 3 productos menos solicitados en la regi�n �Pacific�go
CREATE OR ALTER PROCEDURE sp_productoMasSolicitadoEuropa
as begin 
	BEGIN TRY
		BEGIN TRANSACTION
		SELECT * FROM OPENQUERY (SERVIDOR3, 'select top 3 so.ProductID, COUNT(*) as Cantidad_Productos
		from EuropePacific.sales.SalesOrderDetail so
		inner join EuropePacific.sales.SalesOrderHeader sh
		on sh.SalesOrderID = so.SalesOrderID 
		where sh.TerritoryID = 9 
		group by so.ProductID
		order by 2 asc
		')
		COMMIT TRANSACTION
	END TRY 
	BEGIN CATCH   
		ROLLBACK TRANSACTION   
		RAISERROR ('No se pudo realizar la accion',16,1)  
	END CATCH
end
goexec sp_productoMasSolicitadoEuropa-- 7.  Actualizar la subcategor�a de los productos con productId del 1 al 4 a la subcategor�a valida 
-- para el tipo de producto gocreate or alter procedure sp_ActualizarSubcategoria(
	@productId int,
	@subcategoriaID int
)
as
	IF exists( select *	from AdventureWorks2019.Production.Product where ProductID = @productId )
	BEGIN
		IF exists( select * from AdventureWorks2019.Production.ProductSubcategory where ProductSubcategoryID = @subcategoriaID )
			update AdventureWorks2019.Production.Product
			set [ProductSubcategoryID] = @subcategoriaID, ModifiedDate = GETDATE()
			where ProductID = @productId
		ELSE
			SELECT -1 as resultado
	END
	ELSE
		SELECT 0 as resultado
-- 9. Listar los clientes del territorio 1 y 4 que no tengan asociado un valor en personId
SELECT CustomerID FROM Sales.Customer WHERE (TerritoryID = 1 or TerritoryID = 4) AND PersonID IS NULL

go
CREATE OR ALTER PROCEDURE sp_listarClientesTerritorio1a4
as begin 
	BEGIN TRY
		BEGIN TRANSACTION
	SELECT CustomerID FROM Sales.Customer WHERE (TerritoryID = 1 or TerritoryID = 4) AND PersonID IS NULL
		COMMIT TRANSACTION
	END TRY 
	BEGIN CATCH   
		ROLLBACK TRANSACTION   
		RAISERROR ('No se pudo realizar la accion',16,1)  
	END CATCH
end
go

exec sp_listarClientesTerritorio1a4

-- 4. Listar el producto m�s solicitado en la regi�n �Europe�
SELECT * FROM Sales.Customer C INNER JOIN Sales.SalesTerritory ST 
ON C.TerritoryID = ST.TerritoryID WHERE [ST].[Group] = 'Europe';

go
CREATE OR ALTER PROCEDURE sp_productoMasSolicitadoEurope
as begin 
	BEGIN TRY
		BEGIN TRANSACTION
	SELECT * FROM OPENQUERY(SERVIDOR3,'SELECT * FROM EuropePacific.Sales.Customer')
		COMMIT TRANSACTION
	END TRY 
	BEGIN CATCH   
		ROLLBACK TRANSACTION   
		RAISERROR ('No se pudo realizar la accion',16,1)  
	END CATCH
end
go

exec sp_productoMasSolicitadoEurope

-- Listar los clientes del territorio 1 que tengan ordenes en otro territorio
go
CREATE OR ALTER PROCEDURE sp_clientesTerritorio1
as begin 
	BEGIN TRY
		BEGIN TRANSACTION
	SELECT * FROM OPENQUERY(SERVIDOR3, 'select * from NorthAmerica.sales.SalesOrderHeader 
	where TerritoryID = 1 and CustomerID in ( select CustomerID from NorthAmerica.sales.SalesOrderHeader 
	where TerritoryID between 2 and 6 )
	union all
	select * from NorthAmerica.sales.SalesOrderHeader 
	where TerritoryID = 1 and CustomerID in ( select CustomerID from EuropePacific.sales.SalesOrderHeader 
	where TerritoryID between 7 and 10 )')
		COMMIT TRANSACTION
	END TRY 
	BEGIN CATCH   
		ROLLBACK TRANSACTION   
		RAISERROR ('No se pudo realizar la accion',16,1)  
	END CATCH
end
go

exec sp_clientesTerritorio1