package jupiter;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL40.*;

import java.nio.FloatBuffer;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL;

public class Window {
	
	private static Window instance = null;
	
	private int width = 800;
	private int height = 600;
	
	private int vaoID, vboID;
	
	private Model model;
	private Shader shader;
	
	private float[] vertexArray;
	
	private long window;
	
	private Window() {}
	
	public static Window get() {
		if (instance == null) {
			instance = new Window();
		}
		
		return instance;
	}
	
	public void run() {
		init();
		loop();
	}
	
	public void init() {
		glfwInit();
		
		glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
		
		window = glfwCreateWindow(width, height, "Window", 0, 0);
		glfwMakeContextCurrent(window);
		GL.createCapabilities();
		
		shader = new Shader();
		shader.create();
		
		vaoID = glGenVertexArrays();
		glBindVertexArray(vaoID);
		
		model = new Model(new Vector3f(0, 0, 0), new Vector2f(1, 1), new Vector3f(0, 0, 0));
		model.create();
		
		glfwShowWindow(window);
	}
	
	public void loop() {
		while (!glfwWindowShouldClose(window)) {
			shader.use();
			glClearColor(1, 1, 1, 1);
			glClear(GL_COLOR_BUFFER_BIT);
			
			Render.render(vaoID, model);
			
			glfwSwapBuffers(window);
			glfwPollEvents();
			shader.stop();
		}
		
		glDeleteVertexArrays(vaoID);
		model.delete();
		shader.delete();
		
		glfwTerminate();
	}

}
