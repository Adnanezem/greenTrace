public class gestion {
    
    public static Map<String, String> helloUser(Connection cn, String login) throws SQLException {
        Map<String, String> userInfo = new HashMap<>();
    
        String requete = "SELECT nom, prenom FROM user WHERE login = ?";
        PreparedStatement preparedStatement = cn.prepareStatement(requete);
        preparedStatement.setString(1, login);
        ResultSet rs = preparedStatement.executeQuery();
        
        if (rs.next()) {
            userInfo.put("nom", rs.getString("nom"));
            userInfo.put("prenom", rs.getString("prenom"));
        }        
        return userInfo;
    }




}
