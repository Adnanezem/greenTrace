// public class user {

// // connexion à la BDD
    
//     public static Connection connecterBaseDeDonnees() throws SQLException {
//         String url = "jdbc:mysql://localhost:3306/green_trace";
//         String user = "root";
//         String password = "";
//         return DriverManager.getConnection(url, user, password);
//     }
// //  authentifier un user
//     public static boolean authentifierUtilisateur(Connection cn, String login, String motDePasse) throws SQLException {
//         String requete = "SELECT * FROM user WHERE login = ? AND MDPS = ?";
//         PreparedStatement preparedStatement = cn.prepareStatement(requete);
//         preparedStatement.setString(1, login);
//         preparedStatement.setString(2, motDePasse);
//         ResultSet rs = preparedStatement.executeQuery();
//         return rs.next(); //  true si user est authentifié, sinon false
//     }


// //  inscrire un nouvel user
//     public static boolean inscrireUtilisateur(Connection cn, String login, String motDePasse, String nom, String prenom) throws SQLException {
//         //  si user existe déjà
//         String requeteExistence = "SELECT * FROM user WHERE login = ?";
//         PreparedStatement preparedStatementExistence = cn.prepareStatement(requeteExistence);
//         preparedStatementExistence.setString(1, login);
//         ResultSet rsExistence = preparedStatementExistence.executeQuery();
    
//         if (rsExistence.next()) {
//             System.out.println("Un utilisateur avec le login '" + login + "' existe déjà.");
//             return false ;
//         } else {
//             // Insérer user
//             String requeteInsertion = "INSERT INTO user (login, MDPS, nom, prenom) VALUES (?, ?, ?, ?)";
//             PreparedStatement preparedStatementInsertion = cn.prepareStatement(requeteInsertion);
//             preparedStatementInsertion.setString(1, login);
//             preparedStatementInsertion.setString(2, motDePasse);
//             preparedStatementInsertion.setString(3, nom);
//             preparedStatementInsertion.setString(4, prenom);
//             int lignesModifiees = preparedStatementInsertion.executeUpdate();
    
//             if (lignesModifiees > 0) {
//                 System.out.println("Inscription réussie !");
//                  return true;
//             } else {
//                 System.out.println("Échec de l'inscription.");
//                 return false ;
//             }
//         }
//     }

// // se déconnecter de la BDD
//     public static boolean deconnecterBaseDeDonnees(Connection cn) {
//         try {
//             if (cn != null) {
//                 cn.close();
//                 System.out.println("Déconnexion de la base de données réussie.");
//                 return true; // La déconnexion a réussi
//             }
//         } catch (SQLException e) {
//             e.printStackTrace();
//                 return false; // La déconnexion a échoué
//         }
//         return false; // si cnx null
//     }







// }
