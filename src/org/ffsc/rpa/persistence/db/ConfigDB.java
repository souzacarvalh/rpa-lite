package org.ffsc.rpa.persistence.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.ffsc.rpa.domain.Config;
import org.ffsc.rpa.exceptions.RPAExceptionHandler;
import org.ffsc.rpa.persistence.ConnectionFactory;
import org.ffsc.rpa.persistence.interfaces.ConfigDAO;
import org.ffsc.rpa.types.Protocolo;

public class ConfigDB implements ConfigDAO {

	public boolean save(Config cfg) {

		Connection con = null;
		PreparedStatement stmt = null;
		StringBuilder sql = new StringBuilder();

		try {

			con = ConnectionFactory.getConnection(ConnectionFactory.SGDB.MYSQL);

			sql.append("UPDATE config_dir cd INNER JOIN config_email ce ON ce.config_id = cd.config_id");
			sql.append(" SET cd.path_gravacao_xml = ?");
			sql.append(", ce.protocolo = ?");
			sql.append(", ce.servidor  = ?");
			sql.append(", ce.email = ?");
			sql.append(", ce.senha = ?");
			sql.append(", ce.pasta = ?");
			sql.append(", ce.apagar_processados = ?");
			sql.append(" WHERE ce.config_id = 1");

			stmt = con.prepareStatement(sql.toString());

			stmt.setString(1, cfg.getDirGravacao());
			stmt.setString(2, cfg.getProtocolo().toString());
			stmt.setString(3, cfg.getServidor());
			stmt.setString(4, cfg.getEmail());
			stmt.setString(5, cfg.getSenha());
			stmt.setString(6, cfg.getDirCaixaEmail());
			stmt.setInt(7, (cfg.getApagarJaProcessados().booleanValue()) ? 1 : 0);

			int ret = stmt.executeUpdate();

			if (ret > 0)
				return true;

		} catch (Exception e) {
			new RPAExceptionHandler().handle(e);
		} finally {

			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					new RPAExceptionHandler().handle(e);
				}
			}

			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					new RPAExceptionHandler().handle(e);
				}
			}
		}

		return false;
	}

	public Config get() {

		Config configuration = null;

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		StringBuilder sql = new StringBuilder();

		try {

			con = ConnectionFactory.getConnection(ConnectionFactory.SGDB.MYSQL);

			sql.append("SELECT cd.path_gravacao_xml");
			sql.append(", ce.protocolo, ce.servidor");
			sql.append(", ce.email, ce.senha");
			sql.append(", ce.pasta, ce.apagar_processados");
			sql.append(" FROM config_email ce");
			sql.append(" INNER JOIN config_dir cd ON ce.config_id = cd.config_id");
			sql.append(" WHERE ce.config_id = 1");

			stmt = con.prepareStatement(sql.toString());

			rs = stmt.executeQuery();

			if (rs.next()) {

				configuration = new Config();

				configuration.setDirGravacao(rs.getString(1));
				configuration.setProtocolo(Protocolo.valueOf(rs.getString(2)));
				configuration.setServidor(rs.getString(3));
				configuration.setEmail(rs.getString(4));
				configuration.setSenha(rs.getString(5));
				configuration.setDirCaixaEmail(rs.getString(6));
				configuration.setApagarJaProcessados(rs.getBoolean(7));
			}

		} catch (Exception e) {
			new RPAExceptionHandler().handle(e);
		} finally {

			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					new RPAExceptionHandler().handle(e);
				}
			}

			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					new RPAExceptionHandler().handle(e);
				}
			}
		}

		return configuration;
	}
}