package hu.puff.diffsolver.gui;

import java.awt.Toolkit;
import java.util.Locale;
import javax.swing.*;
import javax.swing.text.*;
import java.text.*;

public class DecimalField extends JTextField
{
  private Toolkit toolkit;
  private NumberFormat numberFormatter;

  public DecimalField(Double value, int columns)
  {
    super(columns);
    toolkit = Toolkit.getDefaultToolkit();
    numberFormatter = NumberFormat.getNumberInstance(Locale.US);
    numberFormatter.setParseIntegerOnly(false);
    setValue(value);
  }

  public Double getValue()
  {
    double retVal = 0;

    try
    {
      retVal = numberFormatter.parse(getText()).doubleValue();
    }
    catch(ParseException e)
    {
      toolkit.beep();
    }
    return retVal;
  }

  public void setValue(Double value)
  {
    setText(numberFormatter.format(value));
  }

  protected Document createDefaultModel()
  {
    return new NumberDocument();
  }

  protected class NumberDocument extends PlainDocument
  {
    public void insertString(int offs, String str, AttributeSet a) throws BadLocationException
    {
      char[] source = str.toCharArray();
      char[] result = new char[source.length];
      int j = 0;

      for(int i = 0; i < result.length; i++)
      {
        if( Character.isDigit(source[i]) ||
            source[i] == '-' || source[i] == '.')
        {
          result[j++] = source[i];
        }
        else
        {
          toolkit.beep();
        }
      }
      super.insertString(offs, new String(result, 0, j), a);
    }
  }
}