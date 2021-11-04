/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codigo;

import datos.Enemy;
import datos.Gem;
import datos.Place;
import datos.Superheroe;
import datos.User;
import excepciones.ExcepcionApp;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase que controla la base de datos.
 *
 * @author alu2017363
 */
public class Bddcontrol {

    //Conexión con la base de datos
    private Connection connection;

    /**
     * Pone seis gemas al usuario
     *
     * @param usuario Usuario que ha inciado sesión
     * @param tiposgemas Lista de tipos de gemas
     * @throws SQLException
     */
    public void ponseisgemas(User usuario, String tiposgemas[], ArrayList<String> transaccion) throws SQLException {
        Random rn = new Random();
        String propietario;

        int aleatorio;
        //Se obtienen todos los lugares
        ArrayList<Place> lugares = dametodoslugares();

        //Se quita de la lista el lugar donde está el usuario.
        Metodos.quitalugar(lugares, usuario.getPlace());

        //Se van a poner 6 gemas en lugares aleatorios distintos al que está el usuario y distintos entre ellos
        for (int cont = 0; cont < 6; cont++) {
            //Se obtiene un lujgar aleatorio
            aleatorio = rn.nextInt(lugares.size());

            //Si el lugar tiene un villano pasará a ser propietario
            propietario = dameunvillano(lugares.get(aleatorio));

           /*if (propietario == null) {
                //Si el lugar no tiene un villano el propietario será el usuario.
                propietario = usuario.getUsername();
            }*/
           
            //Se crea una gema en el lugar nuevo de un tipo distinto en cada una de las 6 veces con el propietario elegido. 
            creagema(usuario, lugares.get(aleatorio), tiposgemas[cont], propietario, transaccion);
            //Se elimina de la lista de lugares el lugar elegido
            lugares.remove(aleatorio);
        }
    }

    /**
     * Da el nombre de un villano al azar de los que hay en un lugar concreto
     * @param lugar Lugar donde buscar
     * @return
     * @throws SQLException
     */
    private String dameunvillano(Place lugar) throws SQLException {

        Random rn = new Random();
        int aleatorio;
        //Se obtienen los villanos del lugar.
        ArrayList<Enemy> villanoslugar = damevillanoslugar(lugar.getName());
        if (villanoslugar.size() > 0) {
            //Si hay villanos en el lugar se elige uno aleatorio.
            aleatorio = rn.nextInt(villanoslugar.size());
            //Se devuelve
            return villanoslugar.get(aleatorio).getName();
        } else {
            //Si no hay villanos en el lugar se devuelve null
            return null;
        }

    }

    /**
     * Cambia el lugar del jugador a un destino especificado.
     * @param usuario Usuario que ha iniciado sesión
     * @param destino Nombre del destino
     * @throws SQLException 
     */
    public void cambialugarjugador(User usuario, String destino) throws SQLException {
        //Se actualiza el lugar del usuario en la base de datos.
        PreparedStatement ps = connection.prepareStatement("update user set place=? where username=?;");
        ps.setString(1, destino);
        ps.setString(2, usuario.getUsername());
        // Ejecutamos la consulta de insert/update/delete executeUpdate()
        ps.executeUpdate();
        // Cerramos recursos
        ps.close();
        usuario.setPlace(destino);
    }

    /**
     * Borra un usuario
     * @param username Usuario a borrar
     * @throws SQLException 
     */
    public void borrarusuario(String username) throws SQLException {
        //Se borra el usuario de la base de datos.
        PreparedStatement ps = connection.prepareStatement("DELETE FROM user WHERE username=?;");
        ps.setString(1, username);

        // Ejecutamos la consulta de insert/update/delete executeUpdate()
        ps.executeUpdate();
        // Cerramos recursos
        ps.close();
    }

    /**
     * Se actualiza el nivel del usuario en la base de datos.
     * @param usuario Usuario a actualizar
     * @throws SQLException 
     */
    public void actualizanivelusuario(User usuario) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("update user set level=? where username=?;");
        ps.setInt(1, usuario.getLevel());
        ps.setString(2, usuario.getUsername());
        // Ejecutamos la consulta de insert/update/delete executeUpdate()
        ps.executeUpdate();
        // Cerramos recursos
        ps.close();
    }

    /**
     * Se quitan las gemas de un villano que son del jugador en el lugar donde está este
     * Ahora se podrán coger.
     * @param villano Villano
     * @throws SQLException 
     */
    public void quitagemas(Enemy villano, String nombreusuario) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("update gem set owner=NULL where owner=? AND place=? AND user=?;");

        ps.setString(1, villano.getName());
        ps.setString(2, villano.getPlace());
        ps.setString(3, nombreusuario);
        // Ejecutamos la consulta de insert/update/delete executeUpdate()
        ps.executeUpdate();
        // Cerramos recursos
        ps.close();
    }

    /**
     * Pone las gemas de un lugar que son del usuario y las ha obtenido a ser propiedad de un villano y las cambia de lugar
     * @param usuario Usuario
     * @param villano Villano
     * @param lugar Lugar
     * @throws SQLException 
     */
    public void pongemasusuariovillanolugar(User usuario, Enemy villano, String lugarnuevo) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("update gem set owner=? set place=? where owner=? AND place=? AND user=?;");

        ps.setString(1, villano.getName());
        ps.setString(2, lugarnuevo);
        ps.setString(3, usuario.getUsername());
        ps.setString(4, villano.getPlace());
        ps.setString(5, usuario.getUsername());

        // Ejecutamos la consulta de insert/update/delete executeUpdate()
        ps.executeUpdate();
        // Cerramos recursos
        ps.close();
    }

    /**
     * Mueve las gemas de un villano, se las lleva con el
     * @param usuario Usuario
     * @param villano Villano
     * @param lugar Lugar
     * @throws SQLException 
     */
    public void muevegemasvillano(Enemy villano, String lugarnuevo) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("update gem set place=? where owner=?;");

        ps.setString(1, lugarnuevo);
        ps.setString(2, villano.getName());
      
        // Ejecutamos la consulta de insert/update/delete executeUpdate()
        ps.executeUpdate();
        // Cerramos recursos
        ps.close();
    }

    
    /**
     * Mueve todas las gemas del jugador porque se las lleva con el
     * @param usuario Usuario
     * @param villano Villano
     * @param lugar Lugar
     * @throws SQLException 
     */
    public void muevegemasjugador(User usuario, String lugarnuevo) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("update gem set place=? where owner=?;");

        ps.setString(1, lugarnuevo);
        ps.setString(2, usuario.getUsername());
        // Ejecutamos la consulta de insert/update/delete executeUpdate()
        ps.executeUpdate();
        // Cerramos recursos
        ps.close();
    }

  
    public void iniciatransaccion() throws SQLException
    {
        connection.setAutoCommit(false);
    }
   
    public void finalizatransaccion() throws SQLException
    {
        connection.commit();
        connection.setAutoCommit(true);
    }
    
    public void vuelveatras() throws SQLException
    {
        connection.rollback();
    }
    
    public void ejecutatransaccion(ArrayList<String> transaccion) throws SQLException
    {
        PreparedStatement ps = null;
        
        try
        {
            connection.setAutoCommit(false);
            for(String operacion : transaccion)
            {
                ps = connection.prepareStatement(operacion);
                ps.executeUpdate();
            }
            connection.commit();
        }
        catch(SQLException ex)
        {
            System.out.println("Error");
            connection.rollback();
        }
        finally
        {
            
            try {
                ps.close();
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                Logger.getLogger(Bddcontrol.class.getName()).log(Level.SEVERE, null, ex);
            }
        }   
    }
    
     /**
     * Crea una gema en la base de datos
     * @param usuario Usuario que ha iniciado sesión y de quien será la gema
     * @param lugar Lugar donde se pondrá la gema
     * @param tipogema Tipo de gema
     * @param propietario Propietario de la gema
     * @throws SQLException 
     */
    private void creagema(User usuario, Place lugar, String tipogema, String propietario, ArrayList<String> transaccion) throws SQLException {
       /* PreparedStatement ps = connection.prepareStatement("insert into gem values (?, ?, ?, ?);");
        ps.setString(1, tipogema);
        ps.setString(2, usuario.getUsername());
        ps.setString(3, propietario);
        ps.setString(4, lugar.getName());
        // Ejecutamos la consulta de insert/update/delete executeUpdate()
        ps.executeUpdate();
        // Cerramos recursos
        ps.close();*/
       if (propietario==null)
       {
           propietario = "null";
       }
       else
       {
           propietario = "'" + propietario + "'";
       }
       String sql = "insert into gem values ('"+ tipogema +"','" + usuario.getUsername() + "'," + propietario + ",'" + lugar.getName() + "');";
       
       transaccion.add(sql);
    }

    /**
     * Devuelve todos los lugares
     * @return Lista de lugares
     * @throws SQLException 
     */
    public ArrayList<Place> dametodoslugares() throws SQLException {
        ArrayList<Place> lugares = new ArrayList<Place>();
        Statement st = connection.createStatement();
        String query = "Select * from place";
        ResultSet rs = st.executeQuery(query);
        while (rs.next()) {

            lugares.add(new Place(rs.getString("name"), rs.getString("description"), rs.getString("north"), rs.getString("south"), rs.getString("west"), rs.getString("east")));
        }
        rs.close();
        st.close();
        return lugares;
    }

    /**
     * Devuelve todos los usuarios
     * @return Lista de usuarios
     * @throws SQLException 
     */
    public ArrayList<User> dametodosusuarios() throws SQLException {
        ArrayList<User> usuarios = new ArrayList<>();
        Statement st = connection.createStatement();
        String query = "Select * from user";
        ResultSet rs = st.executeQuery(query);
        while (rs.next()) {
            usuarios.add(new User(rs.getString("username"), rs.getString("superhero"), rs.getInt("level"), rs.getString("place"), rs.getInt("points")));
        }
        rs.close();
        st.close();
        return usuarios;
    }

    /**
     * Devuelve las gemas que hay en un lugar y tienen un nombre concreto
     * @param lugar Lugar donde buscar
     * @param nombregema Nombre de gema
     * @return Lista de gemas
     * @throws SQLException 
     */
    public ArrayList<Gem> damegemalugarnombre(String nombregema, User usuario) throws SQLException {
        ArrayList<Gem> gemas = new ArrayList<>();
        String owner;
        Statement st = connection.createStatement();
        String query = "Select * from gem where place = '" + usuario.getPlace() + "' AND name = '" + nombregema + "' AND user='" + usuario.getUsername() + "';";
        ResultSet rs = st.executeQuery(query);
        while (rs.next()) {
            owner = rs.getString("owner");
            
            /*if (owner.equals("null"))
            {
                owner = null;
            }*/
            gemas.add(new Gem(rs.getString("name"), rs.getString("user"),owner, rs.getString("place")));
        }
        rs.close();
        st.close();
        return gemas;
    }

    /**
     * Devuelve las gemas de un lugar
     * @param lugar Lugar donde buscar
     * @return Lista de gemas
     * @throws SQLException 
     */
    public ArrayList<Gem> damegemaslugar(String lugar, String usuario) throws SQLException {
        ArrayList<Gem> gemas = new ArrayList<>();

        Statement st = connection.createStatement();
        String query = "Select * from gem where place = '" + lugar + "' AND user='" + usuario + "';";
        ResultSet rs = st.executeQuery(query);
        while (rs.next()) {
            gemas.add(new Gem(rs.getString("name"), rs.getString("user"), rs.getString("owner"), rs.getString("place")));
        }
        rs.close();
        st.close();
        return gemas;
    }

    /**
     * Devuelve las gemas de un usuario
     * @param usuario Usuario
     * @return Lista de gemas del usuario
     * @throws SQLException 
     */
    public ArrayList<Gem> damegemausuario(String usuario) throws SQLException {
        ArrayList<Gem> gemas = new ArrayList<>();

        Statement st = connection.createStatement();
        String query = "Select * from gem where owner = '" + usuario +  "' AND user='" + usuario + "';";
        ResultSet rs = st.executeQuery(query);
        while (rs.next()) {
            gemas.add(new Gem(rs.getString("name"), rs.getString("user"), rs.getString("owner"), rs.getString("place")));
        }
        rs.close();
        st.close();
        return gemas;
    }

    /**
     * Comprueba si el jugador ha ganado, lo que significa ha obtenido todas sus gemas
     * @param usuario
     * @return Si ha ganado o no
     * @throws SQLException 
     */
    public boolean compruebaganado(User usuario) throws SQLException
    {
        int numero=-1;
        Statement st = connection.createStatement();
        String query = "Select count(*) as veces from gem where user='" + usuario.getUsername() + "' AND owner='" + usuario.getUsername() + "'";
        ResultSet rs = st.executeQuery(query);
        if (rs.next()) {
            numero = rs.getInt("veces");
        }
        rs.close();
        st.close();
        
        if(numero==6)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    /**
     * Modificar el propietario de una gema poniendo como propietario el usuario
     * @param gema
     * @param usuario
     * @throws SQLException 
     */
    public void modificapropietariogema(Gem gema, User usuario) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("update gem set owner=? where name=? AND user=? AND place=?");

        
        ps.setString(1, usuario.getUsername());

        ps.setString(2, gema.getName());

        ps.setString(3, gema.getUser());

        ps.setString(4, gema.getPlace());

        // Ejecutamos la consulta de insert/update/delete executeUpdate()
        ps.executeUpdate();

        // Cerramos recursos
        ps.close();
    }

    /**
     * Cambia de lugar un villano
     * @param villano Villano a mover
     * @param lugarnuevo Nombre del lugar nuevo
     * @throws SQLException 
     */
    public void cambialugarvillanosinmovergemas(Enemy villano, String lugarnuevo) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("update enemy set place=? where name=?;");

        ps.setString(1, lugarnuevo);
        ps.setString(2, villano.getName());

        // Ejecutamos la consulta de insert/update/delete executeUpdate()
        ps.executeUpdate();
        // Cerramos recursos
        ps.close();
    }

    /**
     * Obtiene la lista de villanos del lugar
     * @param nombrelugar Lugar donde buscar
     * @return Villanos
     * @throws SQLException 
     */
    public ArrayList<Enemy> damevillanoslugar(String nombrelugar) throws SQLException {
        ArrayList<Enemy> villanos = new ArrayList<>();

        Statement st = connection.createStatement();
        String query = "Select * from enemy where place = '" + nombrelugar + "'";
        ResultSet rs = st.executeQuery(query);
        while (rs.next()) {
            villanos.add(new Enemy(rs.getString("name"), rs.getString("debility"), rs.getInt("level"), rs.getString("place")));
        }
        rs.close();
        st.close();
        return villanos;
    }

    /**
     * Obtiene un villano de un lugar concreto y con un nombre concreto
     * @param nombrelugar Lugar donde buscar
     * @param nombre Nombre del villano
     * @return Villano
     * @throws SQLException 
     */
    public Enemy damevillanolugarnombre(String nombrelugar, String nombre) throws SQLException {
        Enemy villano = null;

        Statement st = connection.createStatement();
        String query = "Select * from enemy where place = '" + nombrelugar + "' AND name='" + nombre + "'";
        ResultSet rs = st.executeQuery(query);
        if (rs.next()) {
            villano = new Enemy(rs.getString("name"), rs.getString("debility"), rs.getInt("level"), rs.getString("place"));
        }
        rs.close();
        st.close();
        return villano;
    }

    /**
     * Devuelve todos los villanoos
     * @return Lista de villanos
     * @throws SQLException 
     */
    public ArrayList<Enemy> dametodosvillanos() throws SQLException {
        ArrayList<Enemy> villanos = new ArrayList<>();

        Statement st = connection.createStatement();
        String query = "Select * from enemy;";
        ResultSet rs = st.executeQuery(query);
        while (rs.next()) {
            villanos.add(new Enemy(rs.getString("name"), rs.getString("debility"), rs.getInt("level"), rs.getString("place")));
        }
        rs.close();
        st.close();
        return villanos;
    }

    /**
     * Devuelve la información de un lugar
     * @param nombrelugar Nombre del lugar
     * @return Datos del lugar
     * @throws SQLException 
     */
    public Place damelugar(String nombrelugar) throws SQLException {
        Place lugar = null;
        Statement st = connection.createStatement();
        String query = "Select * from place where name = '" + nombrelugar + "'";
        ResultSet rs = st.executeQuery(query);
        if (rs.next()) {
            lugar = new Place(rs.getString("name"), rs.getString("description"), rs.getString("north"), rs.getString("south"), rs.getString("west"), rs.getString("east"));
        }
        rs.close();
        st.close();
        return lugar;
    }

    /**
     * Comprueba si un usuario existe
     * @param username Nombre de usuario
     * @return Si existe o no
     * @throws SQLException 
     */
    public boolean usuarioexiste(String username) throws SQLException {
        Statement st = connection.createStatement();

        String query = "Select * from user where username = '" + username + "'";
        ResultSet rs = st.executeQuery(query);
        boolean exist = rs.next();
        rs.close();
        st.close();
        return exist;
    }

    /**
     * Comprueba si un superheroe existe
     * @param name Nombre del superheroe
     * @return Si existe o no
     * @throws SQLException 
     */
    public boolean existesuperheroe(String name) throws SQLException {
        Statement st = connection.createStatement();
        String query = "Select * from superhero where name = '" + name + "'";
        ResultSet rs = st.executeQuery(query);
        boolean exist = rs.next();
        rs.close();
        st.close();
        return exist;
    }

    /**
     * Registra un usuario
     * @param usuario Usuario a registrar
     * @throws SQLException 
     */
    public void registrausuario(User usuario, ArrayList<String> transaccion) throws SQLException {
        /*PreparedStatement ps = connection.prepareStatement("insert into user values (?, ?, ?, ?, ?, ?);");
        ps.setString(1, usuario.getUsername());
        ps.setString(2, usuario.getPassword());
        ps.setInt(3, usuario.getLevel());
        ps.setString(4, usuario.getSuperhero());
        ps.setString(5, usuario.getPlace());
        ps.setInt(6, usuario.getPoints());
        // Ejecutamos la consulta de insert/update/delete executeUpdate()
        ps.executeUpdate();
        // Cerramos recursos
        ps.close();*/
        
        
        String sql = "insert into user values ('" + usuario.getUsername() +  "','" + usuario.getPassword() + "'," + usuario.getLevel() + ",'" + usuario.getSuperhero() + "','" + usuario.getPlace() + "'," + usuario.getPoints() + ");";
        
        transaccion.add(sql);
        
    }

    /**
     * Obtiene la información de un usuario por el nombre
     * @param username Nombre de usuario
     * @return Información del usuario.
     * @throws SQLException 
     */
    public User dameinfouser(String username) throws SQLException {
        User usuario = null;
        Statement st = connection.createStatement();
        String query = "Select * from user where username = '" + username + "'";
        ResultSet rs = st.executeQuery(query);
        if (rs.next()) {
            // public User(String username, String password, String superhero, int level, String place, int points) {

            usuario = new User(rs.getString("username"), rs.getString("superhero"), Integer.parseInt(rs.getString("level")), rs.getString("place"), Integer.parseInt(rs.getString("points")));

        }
        rs.close();
        st.close();
        return usuario;
    }

    /**
     * Constructor. Se conecta a la base de datos.
     * @throws SQLException 
     */
    public Bddcontrol() throws SQLException {
        conectar();
    }

    /**
     * Se realiza la conexión con la base de datos.
     * @throws SQLException 
     */
    public void conectar() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/marvel";
        String user = "root";
        String password = "";
        connection = (Connection) DriverManager.getConnection(url, user, password);
    }

    /**
     * Devuelve la lista de superheroes
     * @return Lista de superheroes
     * @throws SQLException 
     */
    public ArrayList<Superheroe> consultasuperheroes() throws SQLException {
        ArrayList<Superheroe> superheroes = new ArrayList<>();
        Statement st = connection.createStatement();
        String select = "select * from superhero";
        ResultSet rs = st.executeQuery(select);
        while (rs.next()) {
            Superheroe heroe = Metodos.creasuperheroe(rs);
            superheroes.add(heroe);
        }
        rs.close();
        st.close();
        return superheroes;
    }

    /**
     * Comprueba que el nombre de usuario y password son correctos.
     * @param username Nombre de usuario.
     * @param password Password.
     * @return Si es correcto o no.
     * @throws SQLException 
     */
    public boolean iniciasesion(String username, String password) throws SQLException {
        Statement st = connection.createStatement();
        String query = "Select * from user where username = '" + username + "'";
        ResultSet rs = st.executeQuery(query);
        boolean encontrado = false;
        try {
            if (rs.next()) {
                if (rs.getString("pass").equals(password)) {
                    encontrado = true;
                } else {
                    throw new ExcepcionApp(ExcepcionApp.LOGININCORRECTO);
                }
            } else {
                throw new ExcepcionApp(ExcepcionApp.LOGININCORRECTO);
            }
        } catch (ExcepcionApp ex) {
            System.out.println(ex.getMessage());
        } finally {
            rs.close();
            st.close();
            return encontrado;
        }
    }

    /**
     * Desconexión
     * @throws SQLException 
     */
    public void disconnect() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }
}
