
package aplicacion;

import accesodatos.CustomerImp;
import java.util.Scanner;

import accesodatos.IAccesoDatosCustomer;
import accesodatos.IAccesoDatosOrderHeader;
import accesodatos.IAccesoDatosProduct;
import accesodatos.IAccesoDatosSalesOrderDetail;
import accesodatos.IAccesoDatosSpecialOffer;
import accesodatos.ProductionProductImp;
import accesodatos.SalesOrderDetailImpl;
import accesodatos.SalesOrderHeaderImp;
import accesodatos.SalesSpecialOfferImp;
import domain.ProductionProduct;
import domain.SalesCustomer;
import domain.SalesOrderDetail;
import domain.SalesOrderHeader;
import domain.SalesSpecialOffer;

public class app {
    public static void main(String[] args) {
        menu();
    }

    public static void menu() {
        int opc = 0;
        Scanner entrada = new Scanner(System.in);
        do {
            System.out.println("Elige una opcion");
            System.out.println("1. Listar datos de empleado que vendio mas por region");
            System.out.println("2. Listar cuanto vendio un cierto empleado por region");
            System.out
                    .println("3. Listar los datos del cliente con más ordenes solicitadas en la región North America");
            System.out.println("4. Listar los productos que no estan disponibles a la venta");
            System.out.println("5. Listar las ofertas que tienen los productos de la categoría “Bikes”");
            System.out.println("6. Listar el producto más solicitado en la región “Europe” ");
            System.out.println(
                    "7. Actualizar la subcategoría de los productos con productId del 1 al 4 a la subcategoría valida para el tipo de producto ");
            System.out
                    .println("8. Listar los clientes del territorio 1 y 4 que no tengan asociado un valor en personId");
            System.out.println("9. Listar el producto más solicitado en la región “Europe”");
            System.out.println("10. Listar los clientes del territorio 1 que tengan ordenes en otro territorio");        
            System.out.println("11. Salir");
            opc = entrada.nextInt();
            switch (opc) {
                case 1:
                    SalesOrderHeader orderHeader = new SalesOrderHeader();

                    try {
                        IAccesoDatosOrderHeader dao = new SalesOrderHeaderImp();
                        System.out.print("SalesPersonID");
                        System.out.println("\tNumeroOrdenesAtendidas");
                        for (SalesOrderHeader oh : dao.listar()) {
                            System.out.print(oh.getSalesPersionId());
                            System.out.println("\t" + "\t" + oh.getNumeroOrdenes());
                        }
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                    break;
                case 2:
                    orderHeader = null;
                    System.out.println("Ingrese el ID del empleado");
                    int SalesPersonID = entrada.nextInt();

                    try {
                        SalesOrderHeader orderHeader2 = new SalesOrderHeader();
                        orderHeader2.setSalesPersionId(SalesPersonID);
                        IAccesoDatosOrderHeader dao = new SalesOrderHeaderImp();
                        System.out.print("SalesPersonID");
                        System.out.println("\tNumeroOrdenesAtendidas");
                        for (SalesOrderHeader oh : dao.ordenesPorTerritorio(orderHeader2)) {
                            System.out.print(oh.getSalesPersionId());
                            System.out.println("\t" + "\t" + oh.getNumeroOrdenes());
                        }
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                    break;
                case 3:
                    try {
                        IAccesoDatosCustomer dao = new CustomerImp();
                        System.out.print("CustomerID");
                        System.out.println("\tNumberOfOrders");
                        for (SalesCustomer c : dao.listarNorthAmerica()) {
                            System.out.print(c.getCustomerID());
                            System.out.println("\t" + "\t" + c.getNumberOfOrders());
                        }
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                    break;
                case 4:
                    IAccesoDatosProduct dao = new ProductionProductImp();
                    try {
                        for (ProductionProduct p : dao.listar()) {
                            System.out.print(p.getProductID());
                            System.out.print("\t" + p.getName());
                            System.out.print("\t" + p.getProductNumber());
                            System.out.print("\t" + p.getMakeFlag());
                            System.out.print("\t" + p.getFinishedGoodsFlag());
                            System.out.print("\t" + p.getColor());
                            System.out.print("\t" + p.getSafetyStockLevel());
                            System.out.println("\t" + p.getReorderPoint());
                        }
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                    break;
                case 5:

                    try {
                        IAccesoDatosSpecialOffer dao4 = new SalesSpecialOfferImp();
                        for (SalesSpecialOffer so : dao4.listar()) {
                            System.out.print(so.getSpecialOfferID() + "\t" + "\t");
                            System.out.print(so.getDescription() + "\t" + "\t");
                            System.out.print(so.getDiscountPct() + "\t" + "\t");
                            System.out.print(so.getType() + "\t" + "\t");
                            System.out.print(so.getCategory() + "\t" + "\t");
                            System.out.print(so.getStartDate() + "\t" + "\t");
                            System.out.print(so.getEndDate() + "\t" + "\t");
                            System.out.print(so.getMinQty() + "\t" + "\t");
                            System.out.print(so.getMaxQty() + "\t" + "\t");
                            System.out.print(so.getRowguid() + "\t" + "\t");
                            System.out.println(so.getModifiedDate());

                        }
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                    break;
                case 6:
                    try {
                        IAccesoDatosSalesOrderDetail dao5 = new SalesOrderDetailImpl();
                        System.out.println("ProductID" + "\t" + "Cantidad_Productos");
                        for (SalesOrderDetail sod : dao5.listar()) {
                            System.out.print(sod.getProductID() + "\t" + "\t");
                            System.out.println(sod.getCantidad_Productos());
                        }
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                    break;
                case 7:
                    try {
                        IAccesoDatosProduct dao6 = new ProductionProductImp();
                        ProductionProduct product = new ProductionProduct();

                        System.out.println("Ingrese el ProductID: ");
                        int ProductID = entrada.nextInt();
                        System.out.println("Ingrese la subcategoria: ");
                        int SubCategory = entrada.nextInt();
                        product.setProductID(ProductID);
                        product.setProductSubCategoryID(SubCategory);

                        dao6.actualizar(product);
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                    break;
                case 8:
                    try {
                        IAccesoDatosCustomer dao7 = new CustomerImp();
                        System.out.println("CustomerID");
                        for (SalesCustomer sc : dao7.listarClientesTerritorio()) {
                            System.out.println(sc.getCustomerID());
                        }

                    } catch (Exception e) {
                        System.out.println(e);
                    }
                    break;
                case 9:
                    try{
                        IAccesoDatosCustomer dao8 = new CustomerImp();
                        for (SalesCustomer sc : dao8.listarProductoEuropa()){
                            System.out.print(sc.getCustomerID() + "\t");
                            System.out.print(sc.getPersonID() + "\t");
                            System.out.print(sc.getStoreID() + "\t");
                            System.out.print(sc.getTerritoryID() + "\t");
                            System.out.print(sc.getAccountNumber() + "\t");
                            System.out.print(sc.getRowguid() + "\t");
                            System.out.println(sc.getModifiedDate() + "\t");
                        }
                    }catch (Exception e){
                        System.out.println(e);
                    }
                    break;
                    case 10:
                    try{
                        IAccesoDatosOrderHeader dao9 = new SalesOrderHeaderImp();
                        for(SalesOrderHeader soh : dao9.clientesTerritorio1()){
                            System.out.println(soh.getSalesOrderId());
                        }
                    }catch(Exception e){
                        System.out.println(e);
                    }
            }
        } while (opc != 11);

    }
}
