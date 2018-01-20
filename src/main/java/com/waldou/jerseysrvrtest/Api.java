/**
 * The MIT License
 *
 * Copyright (c) 2018 Waldo J. Urribarri. http://www.waldou.com/
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in the
 * Software without restriction, including without limitation the rights to use, copy,
 * modify, merge, publish, distribute, sublicense, and/or sell copies of the Software,
 * and to permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE
 * FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */

package com.waldou.jerseysrvrtest;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.waldou.jerseysrvrtest.framework.Book;
import com.waldou.jerseysrvrtest.framework.BooksDatabase;
import com.waldou.jerseysrvrtest.framework.HashMapBooksDatabaseImpl;
import com.waldou.jerseysrvrtest.framework.OperationStatusResponse;

/**
 * @author Waldo Urribarri - www.waldou.com
 *
 * This Connection class is used for testing purposes.
 *
 */
@Path("/")
public class Api {

	private static Logger LOG = Logger.getLogger(Api.class);
	private static BooksDatabase myDB = new HashMapBooksDatabaseImpl();
	
	static {
		try {
			LOG.info("Initializing books list");
			myDB.addBook(new Book("Java: The Complete Reference, Ninth Edition"));
			myDB.addBook(new Book("Cracking the Coding Interview: 189 Programming Questions and Solutions"));
			myDB.addBook(new Book("Beginning Android Games"));
			myDB.addBook(new Book("Streaming, Sharing, Stealing: Big Data and the Future of Entertainment (MIT Press)"));
			myDB.addBook(new Book("Designing Data-Intensive Applications: The Big Ideas Behind Reliable, Scalable, and Maintainable Systems"));
			LOG.info("Initial load: " + myDB.getAllBooks());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Api() {
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("books")
	public Object getBooks() {
		try {
			LOG.info("Returning books list");
			return myDB.getAllBooks();
		} catch(Exception e) {
			LOG.error("Getting books list failed", e);
			return new OperationStatusResponse(-3, "Unknown error");
		}		
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("books/{bookId}")
	public Object getBook(@PathParam("bookId") Long bookId) {
		try {
			Object obj = myDB.getBookWithId(bookId);
			if(obj == null) {
				LOG.error("Book not found: " + bookId);
				return new OperationStatusResponse(-1, "Book not found", bookId);
			}			
			LOG.info("Returning book with id: " + bookId);
			return obj;
		} catch(Exception e) {
			LOG.error("Getting book failed", e);
			return new OperationStatusResponse(-3, "Unknown error");
		}
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("books")
	public OperationStatusResponse addBook(Book op) {
		if(op.getName() == null) {
			LOG.error("Adding book failed - Invalid data: " + op.toString());
			return new OperationStatusResponse(-2, "Invalid data");
		}
		OperationStatusResponse response = null;
		try {
			LOG.info("Adding book with name: " + op.getName());
			Book book = myDB.addBook(op);
			response = new OperationStatusResponse(0, "Success", op.getId());
			response.setBookId(book.getId());
		} catch(Exception e) {
			LOG.error("Adding book failed", e);
			response = new OperationStatusResponse(-3, "Unknown error");
		}
		return response;
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("books/{bookId}")
	public OperationStatusResponse updateBook(@PathParam("bookId") Long bookId, Book op) {
		if(op.getName() == null) {
			LOG.error("Updating book failed - Invalid data: " + op.toString());
			return new OperationStatusResponse(-2, "Invalid data", bookId);
		}
		try {
			op.setId(bookId);
			LOG.info("Updating book with id: " + bookId);
			Book old = myDB.updateBook(op);
			LOG.info("Old name: " + old.getName() + "; New name: " + op.getName());
			return new OperationStatusResponse(0, "Success", bookId);
		} catch(Exception e) {
			LOG.error("Updating book failed", e);
			return new OperationStatusResponse(-3, "Unknown error");
		}
	}
	
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("books/{bookId}")
	public OperationStatusResponse deleteBook(@PathParam("bookId") Long bookId) {
		try {
			LOG.info("Deleting book with id: " + bookId);
			myDB.removeBookWithId(bookId);
			return new OperationStatusResponse(0, "Success", bookId);
		} catch(Exception e) {
			LOG.error("Removing book failed", e);
			return new OperationStatusResponse(-3, "Unknown error");
		}
	}

}
