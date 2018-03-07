package com.Hhl.Servlets;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class StringContentServlet extends HttpServlet {



	/**
	 * 
	 */
	private static final long serialVersionUID = -2114240648869949824L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("This is do get");
		
		
		Writer out = response.getWriter();
		out.write("sd");
	}
}


