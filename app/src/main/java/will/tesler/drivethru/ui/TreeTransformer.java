package will.tesler.drivethru.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.view.AndroidTreeView;

import butterknife.Bind;
import butterknife.ButterKnife;
import will.tesler.drivethru.R;
import will.tesler.drivethru.adapter.UniversalAdapter;
import will.tesler.drivethru.adapter.UniversalSubject;
import will.tesler.drivethru.analysis.models.DependencyTree;
import will.tesler.drivethru.language.models.Token;

public class TreeTransformer extends UniversalAdapter.Transformer<DependencyTree> {

    @Bind(R.id.viewgroup_tree) ViewGroup mViewGroupTree;

    public TreeTransformer(ViewGroup parent) {
        super(R.layout.row_tree, parent);
        ButterKnife.bind(this, getView());
    }

    @Override
    protected void transform(DependencyTree dependencyTree, final UniversalSubject universalSubject) {
        mViewGroupTree.removeAllViews();

        DependencyTree.Node dependencyRoot = dependencyTree.getRoot();

        TreeNode androidTree = TreeNode.root();
        androidTree.addChild(convertDependencyTreeToAndroidTree(dependencyRoot));

        AndroidTreeView treeView = new AndroidTreeView(getContext(), androidTree);
        mViewGroupTree.addView(treeView.getView());
        treeView.expandAll();
    }

    private TreeNode convertDependencyTreeToAndroidTree(DependencyTree.Node dependencyRoot) {
        TreeNode treeRoot = new TreeNode(dependencyRoot.token);
        treeRoot.setViewHolder(new TreeItemViewHolder(getContext()));

        for (DependencyTree.Node node : dependencyRoot.children) {
            treeRoot.addChild(convertDependencyTreeToAndroidTree(node));
        }
        return treeRoot;
    }

    public class TreeItemViewHolder extends TreeNode.BaseNodeViewHolder<Token> {

        @Bind(R.id.textview_label) TextView mTextViewLabel;
        @Bind(R.id.textview_left) TextView mTextViewLeft;
        @Bind(R.id.textview_lemma) TextView mTextViewLemma;

        public TreeItemViewHolder(Context context) {
            super(context);
        }

        @Override
        public View createNodeView(TreeNode node, Token token) {
            final View view = LayoutInflater.from(context).inflate(R.layout.item_tree, null, false);
            ButterKnife.bind(this, view);

            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < node.getLevel() - 1; i++) {
                stringBuilder.append("          ");
            }
            if (!stringBuilder.toString().isEmpty()) {
                stringBuilder.append("∟");
            }

            mTextViewLabel.setText(token.dependencyEdge.label);
            mTextViewLeft.setText(stringBuilder.toString());
            mTextViewLemma.setText(getContext().getString(R.string.tree_item_lemma, token.lemma));

            return view;
        }
    }
}
