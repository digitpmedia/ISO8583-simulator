package com.mpc.form;

import javax.swing.JTextArea;

import org.apache.log4j.WriterAppender;
import org.apache.log4j.spi.LoggingEvent;

public class LogAppender extends WriterAppender {
	private static volatile JTextArea textArea = null;
	 
    public static void setTextArea(final JTextArea textArea) {
    	LogAppender.textArea = textArea;
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
                            if (textArea.getText().length() == 0) {
                                textArea.setText(message);
                            } else {
                                textArea.insert(message,textArea.getText().length());
                                textArea.getSelectionEnd();
                            }
                        }
                    } catch (final Throwable t) {
                        System.out.println("Unable to append log to text area: "
                                + t.getMessage());
                    }
                }
            }).start();
        } catch (final IllegalStateException e) {
            e.printStackTrace();
        }
	}

	
}
