package it.unipi.dsmt.app.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import it.unipi.dsmt.app.dtos.MessageDTO;

// To access the message info in the database
public class MessageDAO {
    private Connection messageConnection = null;

    public MessageDAO(Connection db) {
        messageConnection = db;
    }

    // Retrieve the messages of the specified chat
    public ArrayList<MessageDTO> getMessagesFromChatId(int chatId) throws SQLException {
        ArrayList<MessageDTO> result = new ArrayList<>();
        String sqlString = "SELECT content, sender, creationTime FROM message WHERE chatID=? ORDER BY creationTime";
        PreparedStatement statement = messageConnection.prepareStatement(sqlString);
        statement.setInt(1, chatId);
        ResultSet set = statement.executeQuery();
        while (set.next()) {
            MessageDTO message = new MessageDTO(set.getString("content"), set.getString("sender"),
                    set.getTimestamp("creationTime"));
            result.add(message);
        }
        return result;
    }

    // Delete all the messages of the specified chat
    public void deleteMessageFromChatID(int chatID) throws SQLException {
        String sqlString = "DELETE FROM message WHERE chatID=?";
        PreparedStatement statement = messageConnection.prepareStatement(sqlString);
        statement.setInt(1, chatID);
        statement.executeUpdate();
    }

    // Delete a specified message
    public void deleteMessageFromMessageID(int messageID) throws SQLException {
        String sqlString = "DELETE FROM message WHERE id=?";
        PreparedStatement statement = messageConnection.prepareStatement(sqlString);
        statement.setInt(1, messageID);
        statement.executeUpdate();
    }
}
