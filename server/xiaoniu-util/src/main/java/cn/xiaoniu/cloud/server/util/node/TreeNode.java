package cn.xiaoniu.cloud.server.util.node;

import cn.xiaoniu.cloud.server.util.constant.CommonConstant;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 孔明
 * @date 2019/12/19 14:53
 * @description cn.xiaoniu.cloud.server.util.node.TreeNode
 */
@Getter
@Setter
public class TreeNode<T extends TreeNode> implements Serializable {

    private static final long serialVersionUID = -4277725726288032531L;

    /**
     * ID 主键
     */
    private Long id;

    /**
     * 父级节点ID
     */
    private Long pId;

    /**
     * 名称
     */
    private String name;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 子节点
     */
    private List<T> children;

    public TreeNode() {
        this(0L, 0L, CommonConstant.CHAR_EMPTY, 0);
    }

    public TreeNode(Long id, Long pId, int sort) {
        this(id, pId, null, sort);
    }

    public TreeNode(Long id, Long pId, String name) {
        this(id, pId, name, 0);
    }

    public TreeNode(Long id, Long pId, String name, int sort) {
        this.id = id;
        this.pId = pId;
        this.name = name;
        this.sort = sort;
    }

    /**
     * 添加子节点
     *
     * @param node 子节点
     */
    public void addChild(T node) {
        if (this.children == null) {
            this.children = new ArrayList<>();
        }
        this.children.add(node);
    }


}
