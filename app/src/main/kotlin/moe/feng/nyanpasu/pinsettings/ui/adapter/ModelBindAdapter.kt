package moe.feng.nyanpasu.pinsettings.ui.adapter

import android.content.Context
import android.support.annotation.CallSuper
import android.support.v7.widget.RecyclerView
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class ModelBindAdapter<T: ModelBindAdapter.IAdapterModel>(private var binder: ModelBinder<T>? = null)
	: RecyclerView.Adapter<ModelBindAdapter.ViewHolder>() {

	var items: MutableList<T> = mutableListOf()

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		val view = LayoutInflater.from(parent.context).inflate(items[0].getLayoutId(), parent, false)
		val holder = ViewHolder(this, view)
		binder!!.onViewCreated(view, holder)
		return holder
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		binder!!.onBind(holder, items[position])
	}

	override fun getItemCount(): Int = items.size

	class ViewHolder(private val bindAdapter: ModelBindAdapter<*>, itemView: View): RecyclerView.ViewHolder(itemView) {

		private var viewArrays: SparseArray<View> = SparseArray()

		val context: Context get() = itemView.context

		operator fun <T: View> get(viewIndex: Int): T = viewArrays[viewIndex] as T

		operator fun set(viewIndex: Int, view: View) {
			viewArrays.put(viewIndex, view)
		}

		fun <T: IAdapterModel> getCurrentData(): T = bindAdapter.items[adapterPosition] as T

		class Property<T: View>(private val viewIndex: Int): ReadWriteProperty<ViewHolder, T> {

			override fun getValue(thisRef: ViewHolder, property: KProperty<*>): T = thisRef[viewIndex]

			override fun setValue(thisRef: ViewHolder, property: KProperty<*>, value: T) {
				thisRef[viewIndex] = value
			}

		}

	}

	abstract class ModelBinder<in T: IAdapterModel> {

		private var bindViewCount = 0

		private var needBindViews = mutableListOf<Pair<Int, Int>>() // view index to view id

		@CallSuper open fun onViewCreated(view: View, holder: ViewHolder) {
			needBindViews.forEach { holder[it.first] = view.findViewById(it.second) }
		}

		abstract fun onBind(holder: ViewHolder, data: T)

		protected fun <T: View> bindHolderView(viewIndex: Int = -1): ViewHolder.Property<T> {
			var temp = viewIndex
			if (temp == -1) {
				temp = bindViewCount
				bindViewCount++
			}
			return ViewHolder.Property(temp)
		}

		protected fun <T: View> autoBindView(viewId: Int, viewIndex: Int = -1): ViewHolder.Property<T> {
			var temp = viewIndex
			if (temp == -1) {
				temp = bindViewCount
				bindViewCount++
			}
			needBindViews.add(temp to viewId)
			return ViewHolder.Property(temp)
		}

	}

	interface IAdapterModel {

		fun getLayoutId(): Int

	}

}