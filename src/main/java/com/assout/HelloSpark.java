package com.assout;

import static spark.Spark.*;
import static spark.SparkBase.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class HelloSpark {

	public static void main(String[] args) throws Exception {
		try {
			proc();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	public static void proc() throws Exception {
		ipAddress(Optional.ofNullable(System.getenv("IP_ADDRESS")).orElse("0.0.0.0"));
		port(Integer.parseInt(Optional.ofNullable(System.getenv("PORT")).orElse("5353")));

// TODO db layer
		String db_host = Optional.ofNullable(System.getenv("DB_IP_ADDRESS")).orElse("172.17.0.6");
		String db_port = Optional.ofNullable(System.getenv("DB_PORT")).orElse("3306");
		String db_name = Optional.ofNullable(System.getenv("DB_DATABASE")).orElse("test");
		String databaseUrl = "jdbc:mysql://" + db_host + ":" + db_port + "/" + db_name;
		String db_user = Optional.ofNullable(System.getenv("DB_USER")).orElse("root");
		String db_pass = Optional.ofNullable(System.getenv("DB_PASS")).orElse("mysql");

		ConnectionSource connectionSource = new JdbcConnectionSource(databaseUrl);
		((JdbcConnectionSource) connectionSource).setUsername(db_user);
		((JdbcConnectionSource) connectionSource).setPassword(db_pass);
		TableUtils.createTableIfNotExists(connectionSource, Book.class);

		Dao<Book, String> bookDao = DaoManager.createDao(connectionSource, Book.class);

		get("/", (request, response) -> {
			Map<String, Object> viewObjects = new HashMap<>();

			List<Book> queryForAll = new ArrayList<>();
			try {
				queryForAll = bookDao.queryForAll();
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (queryForAll.isEmpty()) {
				viewObjects.put("hasNoBooks", "Welcome, please click \"Register Book\" to begin.");
			} else {
				ArrayList<Book> showBooks = new ArrayList<>();
				for (Book book : queryForAll) {
					if (book.readable()) {
						showBooks.add(book);
					}
				}
				viewObjects.put("books", showBooks);
			}
			viewObjects.put("templateName", "bookList.ftl");
			return new ModelAndView(viewObjects, "layout.ftl");
		}, new FreeMarkerEngine());

		get("/book/create", (request, response) -> {
			Map<String, Object> viewObjects = new HashMap<>();
			viewObjects.put("templateName", "bookForm.ftl");
			return new ModelAndView(viewObjects, "layout.ftl");
		}, new FreeMarkerEngine());

		post("/book/create", (request, response) -> {
			String title = request.queryParams("book-title");
			String summary = request.queryParams("book-summary");
			String content = request.queryParams("book-content");

			Book book = new Book(title, summary, content);
			bookDao.create(book);

			response.status(201);
			response.redirect("/");
			return "";
		});

		get("/book/read/:id", (request, response) -> {
			String id = request.params(":id");
			Map<String, Object> viewObjects = new HashMap<>();

			viewObjects.put("templateName", "bookRead.ftl");

			Book queryForId = null;
			try {
				queryForId = bookDao.queryForId(id);
			} catch (Exception e) {
				e.printStackTrace();
			}
			viewObjects.put("book", queryForId);

			return new ModelAndView(viewObjects, "layout.ftl");
		}, new FreeMarkerEngine());

		get("/book/update/:id", (request, response) -> {
			String id = request.params(":id");
			Map<String, Object> viewObjects = new HashMap<>();

			viewObjects.put("templateName", "bookForm.ftl");

			Book queryForId = null;
			try {
				queryForId = bookDao.queryForId(id);
			} catch (Exception e) {
				e.printStackTrace();
			}
			viewObjects.put("book", queryForId);

			return new ModelAndView(viewObjects, "layout.ftl");
		}, new FreeMarkerEngine());

		post("/book/update/:id", (request, response) -> {
			String title = request.queryParams("book-title");
			String summary = request.queryParams("book-summary");
			String content = request.queryParams("book-content");
			String id = request.queryParams("book-id");
			Objects.requireNonNull(id);

			Book queryForId = bookDao.queryForId(id);
			Objects.requireNonNull(queryForId);

			queryForId.setTitle(title);
			queryForId.setContent(content);
			queryForId.setSummary(summary);
			bookDao.update(queryForId);

			response.status(200);
			response.redirect("/");
			return "";
		});


		get("/book/delete/:id", (request, response) -> {
			String id = request.params(":id");
			bookDao.deleteById(id);

			response.status(200);
			response.redirect("/");
			return "";
		});
	}
}