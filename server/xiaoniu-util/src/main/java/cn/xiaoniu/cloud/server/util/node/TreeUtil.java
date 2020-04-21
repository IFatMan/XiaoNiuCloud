package cn.xiaoniu.cloud.server.util.node;

import cn.hutool.core.collection.CollUtil;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 树形结构工具类
 *
 * @author 孔明
 * @date 2019/12/19 16:04
 * @description cn.xiaoniu.cloud.server.util.node.TreeUtil
 */
public class TreeUtil {
    private TreeUtil() {
    }

    /**
     * 将一个List构建成一个数型结构
     *
     * @param nodes 无序所有节点
     * @param root  根节点
     * @return cn.xiaoniu.cloud.server.util.node.TreeNode
     * @author 孔明
     * @date 2019-12-19 16:09:00
     */
    public static <T extends TreeNode<T>> T buildTree(List<T> nodes, T root) {
        if (root == null) {
            return null;
        }
        if (CollUtil.isEmpty(nodes)) {
            return root;
        }

        Map<Long, T> nodeMap = new HashMap<>(nodes.size());
        nodeMap.put(root.getId(), root);
        nodes.stream()
                // 1. 使用ID正序排序避免后边的ID跑到前面 , 在进行四步骤的时候获取不到
                .sorted(Comparator.comparing(T::getId))

                // 2. 将所有的对象放入Map集合方便四步骤使用
                .map(node -> {
                    nodeMap.put(node.getId(), node);
                    return node;
                })

                // 3. 根据sort进行正序排序
                .sorted(Comparator.comparing(TreeNode::getSort))

                // 4. 放到父级的集合中
                .forEach(node -> {
                    if (nodeMap.containsKey(node.getPId())) {
                        nodeMap.get(node.getPId()).addChild(node);
                    }
                });
        return root;
    }

}
