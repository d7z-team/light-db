package edgn.lightdb.memory.internal.universal.table

import edgn.lightdb.memory.internal.universal.TableDelete

/**
 * 一个空的 table 实现
 */
abstract class EmptyMemoryTable<V : Any> : ExpireMemoryTable<V>(), TableDelete
