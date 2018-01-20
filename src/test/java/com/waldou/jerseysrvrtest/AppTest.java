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

import org.json.JSONArray;
import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import junit.framework.TestCase;

/**
 * @author Waldo Urribarri - www.waldou.com
 *
 * Testing suite.
 *
 */
public class AppTest extends TestCase {

	public static final String URL = Main.BASE_URL.replace("%PORT_NUMBER%", "7001");
	
	/**
	 * Test request to get the book list.
	 * @throws UnirestException
	 */
	public void testGetBooks() throws UnirestException {
		HttpResponse<JsonNode> response =
				Unirest.get(URL + "/books")
				.asJson();
		JSONArray arr = response.getBody().getArray();
		assertTrue(arr.length() == 5);
	}
	
	/**
	 * Test request to get a specific book.
	 * @throws UnirestException
	 */
	public void testGetBook() throws UnirestException {
		HttpResponse<JsonNode> response =
				Unirest.get(URL + "/books/2")
				.asJson();
		JSONObject obj = response.getBody().getObject();
		assertEquals("Cracking the Coding Interview: 189 Programming Questions and Solutions", obj.get("name"));
	}
	
	/**
	 * Test request to add a new book.
	 * @throws UnirestException
	 */
	public void testAddBook() throws UnirestException {
		HttpResponse<JsonNode> response =
				Unirest.post(URL + "/books")
				.header("accept", "application/json")
				.header("Content-Type", "application/json")
				.body("{\"name\":\"Java All-in-One For Dummies (For Dummies (Computers))\"}")
				.asJson();
		JSONObject obj = response.getBody().getObject();
		assertTrue((Integer)obj.get("errorCode") == 0);
	}
	
	/**
	 * Test request to update a book.
	 * @throws UnirestException
	 */
	public void testUpdateBook() throws UnirestException {
		HttpResponse<JsonNode> response =
				Unirest.put(URL + "/books/1")
				.header("accept", "application/json")
				.header("Content-Type", "application/json")
				.body("{\"name\":\"Java: The Complete Reference, Tenth Edition (Complete Reference Series)\"}")
				.asJson();
		JSONObject obj = response.getBody().getObject();
		assertTrue((Integer)obj.get("errorCode") == 0);
	}
	
	/**
	 * Test request to remove a book.
	 * @throws UnirestException
	 */
	public void testRemoveBook() throws UnirestException {
		HttpResponse<JsonNode> response =
				Unirest.delete(URL + "/books/3")
				.header("accept", "application/json")
				.header("Content-Type", "application/json")
				.asJson();
		JSONObject obj = response.getBody().getObject();
		assertTrue((Integer)obj.get("errorCode") == 0);
	}

}
