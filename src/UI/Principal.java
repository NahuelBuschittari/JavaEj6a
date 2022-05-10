package UI;

import java.sql.*;
import java.util.LinkedList;
import javax.swing.JOptionPane;

import entities.Product;


public class Principal {
	public static void main(String[] args) {
	try {
		Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
	}catch(InstantiationException | IllegalAccessException | ClassNotFoundException e) {
		e.printStackTrace();
	}
	
	Menu();
	
	}
	
	public static void Menu() {
		int rta=10;
		while (rta!=0) {
			JOptionPane.showMessageDialog(null, "1. Listar productos\n2. Buscar productos\n3. Nuevo producto"
					+ "\n4. Eliminar producto\n5. Modificar producto\n0. Salir");
			rta=Integer.parseInt(JOptionPane.showInputDialog("Ingrese una opcion: "));
	try {
		
		switch (rta) {
		case 0:
			
			break;
		case 1:
			listarProd();
			break;
		case 2:
			buscarProd();
			break;
		case 3:
			newProd();
			break;
		case 4:
			deleteProd();
			break;
		/*case 5:
			updateProd();
			break;*/
		default:
			JOptionPane.showMessageDialog(null, "Error: Las opciones a ingresar son entre 0 y 5 ", null, JOptionPane.ERROR_MESSAGE);
			break;
		}
	}catch(NullPointerException e) {
		JOptionPane.showMessageDialog(null, "Por favor, ingrese un valor.",null,JOptionPane.ERROR_MESSAGE);
	}
		}
	}
	public static void listarProd(){
		Connection conn=null;
		LinkedList<Product> products= new LinkedList<>();
		
		try{
			conn=DriverManager.getConnection("jdbc:mysql://127.0.01/javamarket","root","root");
			//ej query
		Statement stmt= conn.createStatement();
		ResultSet rs=stmt.executeQuery("select * from Product");
		
		while(rs.next()) {
			Product p=new Product();
			p.setIdproduct(rs.getInt("idProduct"));
			p.setName(rs.getString("name"));
			p.setDescription(rs.getString("description"));
			p.setPrice(rs.getDouble("price"));
			p.setStock(rs.getInt("stock"));
			p.setShippingIncluded(rs.getBoolean("shippingIncluded"));
			products.add(p);
		}
		
	
		if(rs!=null) {rs.close();}
		if(stmt!=null) {stmt.close();}
			
			conn.close();
		System.out.println("Listado completo");
		for (Product product : products) {
			System.out.println(product.mostrarDatos());
		}
		System.out.println();
		System.out.println();System.out.println();
	}catch(SQLException ex) {
		System.out.println("SQLException: "+ ex.getMessage());
		System.out.println("SQLState: "+ ex.getSQLState());
		System.out.println("VendorError"+ ex.getErrorCode());
		}
	}
	
	
	public static void buscarProd(){
		Connection conn= null;
		try {
			conn=DriverManager.getConnection("jdbc:mysql://127.0.01/javamarket","root","root");
			//ej query
		PreparedStatement stmt= conn.prepareStatement("select * from product where idproduct=?");
		stmt.setInt(1,Integer.parseInt(JOptionPane.showInputDialog("Ingrese el id del producto: ")));
		
		Product p=new Product();
		
		ResultSet rs= stmt.executeQuery();
		
		if(rs.next()) {
				p.setIdproduct(rs.getInt("idProduct"));
			p.setName(rs.getString("name"));
			p.setDescription(rs.getString("description"));
			p.setPrice(rs.getDouble("price"));
			p.setStock(rs.getInt("stock"));
			p.setShippingIncluded(rs.getBoolean("shippingIncluded"));
		}
		if(rs!=null) {rs.close();}
		if(stmt!=null) {stmt.close();}
			
			conn.close();
		if(p.getIdproduct()!=0) {
			System.out.println("ID        NAME           DESCRIPTION             PRICE        STOCK          SHIPPING INCLUDED?");
			System.out.println();
			System.out.println(p.mostrarDatos());
		}else {
			JOptionPane.showMessageDialog(null, "Id inexistente");
		}
		} catch(SQLException ex) {
			System.out.println("SQLException: "+ ex.getMessage());
			System.out.println("SQLState: "+ ex.getSQLState());
			System.out.println("VendorError"+ ex.getErrorCode());
			}
	}
	
	
	public static void newProd() {
		Connection conn= null;
		
		Product pI= new Product();
		
		pI.setName(JOptionPane.showInputDialog("Ingrese el nombre del producto: "));
		pI.setDescription(JOptionPane.showInputDialog("Ingrese la descripcion del producto: "));
		pI.setPrice(Double.parseDouble(JOptionPane.showInputDialog("Ingrese el precio del producto: ")));
		pI.setStock(Integer.parseInt(JOptionPane.showInputDialog("Ingrese el stock del producto: ")));
		
		int rta= JOptionPane.showConfirmDialog(null, "¿Envio Incluido?: ");
		if(rta==1) {pI.setShippingIncluded(false);}
		else {pI.setShippingIncluded(true);}
	try {
		conn=DriverManager.getConnection("jdbc:mysql://127.0.01/javamarket","root","root");
		
		PreparedStatement stmt= conn.prepareStatement(
		"INSERT INTO product(name,description,price,stock,shippingIncluded) values (?,?,?,?,?)"
		,PreparedStatement.RETURN_GENERATED_KEYS);
		
		stmt.setString(1, pI.getName());
		stmt.setString(2, pI.getDescription());
		stmt.setDouble(3, pI.getPrice());
		stmt.setInt(4, pI.getStock());
		stmt.setBoolean(5, pI.isShippingIncluded());
		stmt.executeUpdate();
		
		ResultSet keyResultSet=stmt.getGeneratedKeys();
		
		if (keyResultSet!=null && keyResultSet.next()) {
			int id=keyResultSet.getInt(1);
			pI.setIdproduct(id);
		}
		
		if(keyResultSet!=null){keyResultSet.close();}
        if(stmt!=null){stmt.close();}
        conn.close();
        
        JOptionPane.showMessageDialog(null, "Se agrego correctamente el producto");
	} catch (SQLException ex) {
		System.out.println("SQLException: "+ ex.getMessage());
		System.out.println("SQLState: "+ ex.getSQLState());
		System.out.println("VendorError"+ ex.getErrorCode());
	}
}
	public static void deleteProd() {
	Connection conn= null;

	try {
		conn=DriverManager.getConnection("jdbc:mysql://127.0.01/javamarket","root","root");
		//ej query
	PreparedStatement stmt= conn.prepareStatement("DELETE FROM product WHERE idProduct=?",
	PreparedStatement.RETURN_GENERATED_KEYS);
	stmt.setInt(1,Integer.parseInt(JOptionPane.showInputDialog("Ingrese el id del producto: "))); //Seteamos parametro
	stmt.executeUpdate(); //Ejecutamos query
	
	if(stmt!=null){stmt.close();}
    conn.close();
    
    JOptionPane.showMessageDialog(null, "Producto eliminado con exito");
    
	}catch (SQLException ex) {
			//Manejo de errores
		System.out.println("SQLException: "+ ex.getMessage());
		System.out.println("SQLState: "+ ex.getSQLState());
		System.out.println("VendorError"+ ex.getErrorCode());
		}
	}
	/*public static void updateProd() {
		Connection conn= null;
		try {
			conn=DriverManager.getConnection("jdbc:mysql://127.0.01/javamarket","root","root");
			PreparedStatement stmt= conn.prepareStatement("SELECT * FROM product WHERE idProduct=?",
			PreparedStatement.RETURN_GENERATED_KEYS);
			stmt.setInt(1,Integer.parseInt(JOptionPane.showInputDialog("Ingrese el id del producto a modificar: ")));
			
			Product p=new Product();
			p.setName(JOptionPane.showInputDialog("Ingrese el nombre del producto: "));
			p.setDescription(JOptionPane.showInputDialog("Ingrese la descripcion del producto: "));
			
			
		} catch (SQLException ex) {
			System.out.println("SQLException: "+ ex.getMessage());
			System.out.println("SQLState: "+ ex.getSQLState());
			System.out.println("VendorError"+ ex.getErrorCode());
		}
		}
	
*/
}

