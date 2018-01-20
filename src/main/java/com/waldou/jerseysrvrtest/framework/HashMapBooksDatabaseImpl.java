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

package com.waldou.jerseysrvrtest.framework;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Waldo Urribarri - www.waldou.com
 *
 * Database implementation using a HashMap.
 *
 */
public class HashMapBooksDatabaseImpl implements BooksDatabase {

	private Map<Long, Book> db = new ConcurrentHashMap<Long, Book>();
	private Long nextId = new Long(1);
	
	@Override
	public Collection<Book> getAllBooks() {
		return db.values();
	}

	@Override
	public Book getBook(Book book) {
		return getBookWithId(book.getId());
	}
	
	@Override
	public Book getBookWithId(Long bookId) {
		return db.get(bookId);
	}

	@Override
	public Book addBook(Book book) {
		book.setId(getNextId());
		db.put(book.getId(), book);
		return book;
	}

	@Override
	public Book updateBook(Book book) throws Exception {
		Book old = db.get(book.getId());
		if(old == null)
			throw new Exception("Book not found");
		return db.put(book.getId(), book);
	}

	@Override
	public Book removeBook(Book book) {
		return removeBookWithId(book.getId());
	}
	
	@Override
	public Book removeBookWithId(Long bookId) {
		return db.remove(bookId);
	}
	
	private synchronized Long getNextId() {
		return nextId++;
	}

}
