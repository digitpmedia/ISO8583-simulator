package com.mpc.ui;

import javax.swing.JTextArea;

import org.apache.log4j.WriterAppender;
import org.apache.log4j.spi.LoggingEvent;

public class UILogAppenderListener extends WriterAppender {
	private static volatile JTextArea textArea = null;
	private final int MAX_LEN = 9999;
    public static void setTextArea(final JTextArea textArea) {
    	UILogAppenderListener.textArea = textArea;
    }
    
	@Override
	public void append(LoggingEvent event) {
		final String message = this.layout.format(event);
		
        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        if (textArea != null) {
                        	if(textArea.getDocument().getLength() > MAX_LEN) {
                        		textArea.setText("");
                        	}
                            textArea.insert(message,textArea.getText().length());
                            textArea.setCaretPosition(textArea.getDocument().getLength()-1);
                        }
                    } catch (final Throwable t) {
                        System.out.println("Unable to append log to text area: "+ t.getMessage());
                    }
                }
            }).start();
        } catch (final IllegalStateException e) {
        }
	}

	
}
