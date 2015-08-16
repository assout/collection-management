//package com.assout;
//
//import static spark.Spark.*;
//import static spark.SparkBase.*;
//
//import java.sql.SQLException;
//import java.util.ArrayDeque;
//import java.util.ArrayList;
//import java.util.Deque;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//
//import spark.ModelAndView;
//import spark.template.freemarker.FreeMarkerEngine;
//
//import com.j256.ormlite.dao.Dao;
//import com.j256.ormlite.dao.DaoManager;
//import com.j256.ormlite.jdbc.JdbcConnectionSource;
//import com.j256.ormlite.support.ConnectionSource;
//import com.j256.ormlite.table.TableUtils;
//
//public class CopyOfHelloSpark {
////	public static Deque<Book> books = new ArrayDeque<>();
//
//	public static void main(String[] args) throws SQLException {
//		ipAddress(Optional.ofNullable(System.getenv("IP_ADDRESS")).orElse("0.0.0.0"));
//		port(Integer.parseInt(Optional.ofNullable(System.getenv("PORT")).orElse("5353")));
//
////		String databaseUrl = "jdbc:mysql://localhost/spark";
//		String databaseUrl = "jdbc:mysql://172.17.0.2/test";
//
//		ConnectionSource connectionSource = new JdbcConnectionSource(databaseUrl);
//		((JdbcConnectionSource) connectionSource).setUsername("root");
//		((JdbcConnectionSource) connectionSource).setPassword("mysql");
//		TableUtils.createTableIfNotExists(connectionSource, Book.class);
//
//		Dao<Book, String> bookDao = DaoManager.createDao(connectionSource, Book.class);
//		get("/", (request, response) -> {
//			Map<String, Object> viewObjects = new HashMap<>();
//
//			List<Book> queryForAll = new ArrayList<>();
//			try {
//				queryForAll = bookDao.queryForAll();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//
//			if (queryForAll.isEmpty()) {
//				viewObjects.put("hasNoBooks", "Welcome, please click \"Write Book\" to begin.");
//			} else {
//				ArrayList<Book> showBooks = new ArrayList<>();
//				for (Book book : queryForAll) {
//					if (book.readable()) {
//						showBooks.add(book);
//					}
//				}
//				viewObjects.put("books", showBooks);
//			}
//			viewObjects.put("templateName", "bookList.ftl");
//			return new ModelAndView(viewObjects, "layout.ftl");
//		}, new FreeMarkerEngine());
//
//		get("/book/create", (request, response) -> {
//			Map<String, Object> viewObjects = new HashMap<>();
//			viewObjects.put("templateName", "bookForm.ftl");
//			return new ModelAndView(viewObjects, "layout.ftl");
//		}, new FreeMarkerEngine());
//
//		post("/book/create", (request, response) -> {
//			String title = request.queryParams("book-title");
//			String summary = request.queryParams("book-summary");
//			String content = request.queryParams("book-content");
//
//			Book book = new Book(title, summary, content);
////			HelloSpark.books.addFirst(book);
//
//				bookDao.create(book);
//
//				response.status(201);
//				response.redirect("/");
//				return "";
//			});
//
//		get("/book/read/:id", (request, response) -> {
////			Integer id = Integer.parseInt(request.params(":id"));
//				String id = request.params(":id");
//				Map<String, Object> viewObjects = new HashMap<>();
//
//				viewObjects.put("templateName", "bookRead.ftl");
//
//				Book queryForId = bookDao.queryForId(id);
//				if (queryForId == null) {
//					throw new NullPointerException("book not found. " + id);
//				}
//				viewObjects.put("book", queryForId);
//
//				return new ModelAndView(viewObjects, "layout.ftl");
//			}, new FreeMarkerEngine());
//
//		get("/book/update/:id", (request, response) -> {
//			Integer id = Integer.parseInt(request.params(":id"));
//			Map<String, Object> viewObjects = new HashMap<>();
//
//			viewObjects.put("templateName", "bookForm.ftl");
//
//			for (Book book : CopyOfHelloSpark.books) {
//				if (id.equals(book.getId())) {
//					viewObjects.put("book", book);
//					break;
//				}
//			}
//
//			return new ModelAndView(viewObjects, "layout.ftl");
//		}, new FreeMarkerEngine());
//
//		post("/book/update/:id", (request, response) -> {
//			String title = request.queryParams("book-title");
//			String summary = request.queryParams("book-summary");
//			String content = request.queryParams("book-content");
//			Integer id = Integer.parseInt(request.queryParams("book-id"));
//
//			for (Book book : CopyOfHelloSpark.books) {
//				if (id.equals(book.getId())) {
//					book.setTitle(title);
//					book.setContent(content);
//					book.setSummary(summary);
//				}
//			}
//
//			response.status(200);
//			response.redirect("/");
//			return "";
//		});
//
//		get("/book/delete/:id", (request, response) -> {
//			Integer id = Integer.parseInt(request.params(":id"));
//
//			for (Book book : CopyOfHelloSpark.books) {
//				if (id.equals(book.getId())) {
//					book.delete();
//				}
//			}
//
//			response.status(200);
//			response.redirect("/");
//			return "";
//		});
//	}
//}