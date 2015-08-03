package android.text;

import android.text.Html.Node;

public interface iNodeBuilder {

  void withinHtml(Node out, Spanned text);
	
}
